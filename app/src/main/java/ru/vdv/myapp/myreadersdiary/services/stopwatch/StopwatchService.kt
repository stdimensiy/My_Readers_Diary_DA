package ru.vdv.myapp.myreadersdiary.services.stopwatch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import ru.vdv.myapp.myreadersdiary.MainActivity
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.model.stopwatch.Stopwatch
import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchFactory
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookFragment.Companion.NOTIFICATION_ALARM_CHANNEL_ID
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookFragment.Companion.NOTIFICATION_FOREGROUND_CHANNEL_ID
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookUiModel
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StopwatchService(
    private val activeStopwatch: Stopwatch = StopwatchFactory.createStopwatch(),
    private val relaxStopwatch: Stopwatch = StopwatchFactory.createStopwatch()
) : Service() {

    private val stopwatchServiceBinder: IBinder by lazy { StopwatchServiceBinder() }
    private var isAlarmRelaxShowed: Boolean = false
    private var isAlarmActiveShowed: Boolean = false
    private var uiModel: ProcessReadingBookUiModel? = null
    private var book: Book? = null
    private var executor: ExecutorService? = null
    private val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private var openProcessReadingBookFragmentIntent: PendingIntent? = null

    @Suppress("deprecation")
    private val notificationForegroundBuilder: NotificationCompat.Builder by lazy {
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationCompat.Builder(this, NOTIFICATION_FOREGROUND_CHANNEL_ID)
        else NotificationCompat.Builder(this))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(NOTIFICATION_TITLE)
    }

    @Suppress("deprecation")
    private val notificationAlarmBuilder: NotificationCompat.Builder by lazy {
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationCompat.Builder(this, NOTIFICATION_ALARM_CHANNEL_ID)
        else NotificationCompat.Builder(this))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(NOTIFICATION_TITLE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_MESSAGE_ID, makeForegroundNotification(NOTIFICATION_TITLE))
        showTimersPush()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return stopwatchServiceBinder
    }

    override fun onDestroy() {
        stopwatchShutdown()
        super.onDestroy()
    }

    private fun showTimersPush() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor().also { executor ->
                executor.execute {
                    try {
                        while (!executor.isShutdown) {
                            showForegroundNotification(
                                "$ACTIVE_STOPWATCH_MESSAGE ${activeStopwatch.getFormattedElapsedTime()}\n" +
                                        "$RELAX_STOPWATCH_MESSAGE ${relaxStopwatch.getFormattedElapsedTime()}"
                            )
                            checkAlarms()
                            Thread.sleep(SHOW_TIMERS_PUSH_DELAY_MS)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun checkAlarms() {
        checkActiveTime()
        checkRelaxTime()
    }

    private fun checkActiveTime() {
        when {
            !isAlarmActiveShowed && activeStopwatch.checkAlarm() -> {
                showAlarmNotification(ALARM_ACTIVE_MESSAGE)
                isAlarmActiveShowed = true
            }
            isAlarmActiveShowed && activeStopwatch.checkAlarm().not() -> isAlarmActiveShowed = false
        }
    }

    private fun checkRelaxTime() {
        when {
            !isAlarmRelaxShowed && relaxStopwatch.checkAlarm() -> {
                showAlarmNotification(ALARM_RELAX_MESSAGE)
                isAlarmRelaxShowed = true
            }
            isAlarmRelaxShowed && relaxStopwatch.checkAlarm().not() -> isAlarmRelaxShowed = false
        }
    }

    private fun makeForegroundNotification(message: String): Notification {
        return notificationForegroundBuilder
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentIntent(openProcessReadingBookFragmentIntent)
            .build()
    }

    private fun showForegroundNotification(message: String) {
        notificationManager.notify(FOREGROUND_MESSAGE_ID, makeForegroundNotification(message))
    }

    private fun showAlarmNotification(alarmMessage: String) {
        notificationAlarmBuilder
            .setContentText(alarmMessage)
            .setContentIntent(openProcessReadingBookFragmentIntent)
            .setAutoCancel(true)
            .build()
            .let { notification -> notificationManager.notify(ALARM_MESSAGE_ID, notification) }
    }

    private fun checkBoundStateAndStopSelf() {
        Executors.newSingleThreadExecutor().let { executorService ->
            executorService.execute {
                Thread.sleep(SHOWDOWN_ON_NO_CLIENTS_SERVICE_DELAY_MS)
                if (activeClients <= ZERO_ACTIVE_CLIENTS) {
                    stopwatchShutdown()
                    stopForeground(true)
                    stopSelf()
                    executorService.shutdown()
                }
            }
        }
    }

    private fun stopwatchShutdown() {
        isAlarmRelaxShowed = false
        isAlarmActiveShowed = false
        activeStopwatch.stop()
        relaxStopwatch.stop()
        executor?.shutdown()
        executor = null
    }

    inner class StopwatchServiceBinder : Binder() {
        /**
         * [uiModelServiceHolder] Служит для сохранения UI-стейта в сервисе. Для получения текущего стейта выполнить вызов с параметром null
         */
        val uiModelServiceHolder: UiModelServiceHolder = { uiModel ->
            uiModel?.let {
                this@StopwatchService.uiModel = uiModel
            }
            this@StopwatchService.uiModel
        }

        fun getActiveStopwatch(): Stopwatch {
            return activeStopwatch
        }

        fun getRelaxStopwatch(): Stopwatch {
            return relaxStopwatch
        }

        fun setCurrentBook(book: Book?) {
            this@StopwatchService.book = book
            this@StopwatchService.openProcessReadingBookFragmentIntent = NavDeepLinkBuilder(baseContext)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.mobile_navigation)
                .setArguments(bundleOf(BaseConstants.MY_BOOK_BUNDLE_KEY to book))
                .setDestination(R.id.nav_book_details_fragment)
                .setDestination(R.id.nav_process_reading_book_fragment)
                .createPendingIntent()
        }

        fun setBoundState(stopwatchServiceBound: Boolean) {
            if (stopwatchServiceBound) activeClients++
            else activeClients--
            if (activeClients <= ZERO_ACTIVE_CLIENTS) checkBoundStateAndStopSelf()
        }
    }

    companion object {
        private var activeClients: Int = 0
        private const val ZERO_ACTIVE_CLIENTS = 0
        private const val SHOWDOWN_ON_NO_CLIENTS_SERVICE_DELAY_MS = 1000L //если нет активных подключений к сервису в течении 1 сек, то сервис останавливается
        private const val SHOW_TIMERS_PUSH_DELAY_MS = 500L
        private const val NOTIFICATION_TITLE = "Контроль чтения"
        private const val ACTIVE_STOPWATCH_MESSAGE = "Время чтения: "
        private const val RELAX_STOPWATCH_MESSAGE = "Время отдыха: "
        private const val ALARM_RELAX_MESSAGE = "Пора приступить к чтению!"
        private const val ALARM_ACTIVE_MESSAGE = "Пора сделать перерыв!"
        private const val FOREGROUND_MESSAGE_ID = 1000
        private const val ALARM_MESSAGE_ID = 1001
    }
}
