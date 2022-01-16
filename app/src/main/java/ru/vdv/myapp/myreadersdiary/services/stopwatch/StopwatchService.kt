package ru.vdv.myapp.myreadersdiary.services.stopwatch

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.model.stopwatch.Stopwatch
import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchFactory
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookFragment.Companion.NOTIFICATION_ALARM_CHANNEL_ID
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookFragment.Companion.NOTIFICATION_FOREGROUND_CHANNEL_ID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StopwatchService(
    private val activeStopwatch: Stopwatch = StopwatchFactory.createStopwatch(),
    private val relaxStopwatch: Stopwatch = StopwatchFactory.createStopwatch()
) : Service() {

    private val stopwatchServiceBinder: IBinder by lazy { StopwatchServiceBinder() }
    private var isAlarmShowed: Boolean = false

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

    private var executor: ExecutorService? = null

    private val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isAlarmShowed = false
        startForeground(FOREGROUND_MESSAGE_ID, makeForegroundNotification(NOTIFICATION_TITLE))
        showTimersPush()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return stopwatchServiceBinder
    }

    override fun onDestroy() {
        isAlarmShowed = false
        activeStopwatch.stop()
        relaxStopwatch.stop()
        executor?.shutdown()
        executor = null
        super.onDestroy()
    }

    private fun showTimersPush() {
        executor = Executors.newSingleThreadExecutor()
        executor?.let { executor ->
            executor.execute {
                try {
                    while (!executor.isShutdown) {
                        showForegroundNotification(
                            "$ACTIVE_STOPWATCH_MESSAGE ${activeStopwatch.getFormattedElapsedTime()}\n" +
                                    "$RELAX_STOPWATCH_MESSAGE ${relaxStopwatch.getFormattedElapsedTime()}"
                        )
                        checkRelaxTime()
                        Thread.sleep(SHOW_TIMERS_PUSH_DELAY_MS)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkRelaxTime() {
        when {
            !isAlarmShowed && relaxStopwatch.getElapsedTime() > MAX_RELAX_TIME_MS -> {
                showAlarmNotification()
                isAlarmShowed = true
            }
            isAlarmShowed && relaxStopwatch.getElapsedTime() < MAX_RELAX_TIME_MS -> isAlarmShowed = false
        }
    }

    private fun makeForegroundNotification(message: String): Notification {
        return notificationForegroundBuilder
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .build()
    }

    private fun showForegroundNotification(message: String) {
        notificationManager.notify(FOREGROUND_MESSAGE_ID, makeForegroundNotification(message))
    }

    private fun showAlarmNotification() {
        notificationAlarmBuilder
            .setContentText(ALARM_MESSAGE)
            .build()
            .let { notification -> notificationManager.notify(ALARM_MESSAGE_ID, notification) }
    }

    inner class StopwatchServiceBinder : Binder() {
        fun getActiveStopwatch(): Stopwatch {
            return activeStopwatch
        }

        fun getRelaxStopwatch(): Stopwatch {
            return relaxStopwatch
        }
    }

    companion object {
        private const val SHOW_TIMERS_PUSH_DELAY_MS = 300L
        private const val NOTIFICATION_TITLE = "Контроль чтения"
        private const val ACTIVE_STOPWATCH_MESSAGE = "Время чтения: "
        private const val RELAX_STOPWATCH_MESSAGE = "Время отдыха: "
        private const val ALARM_MESSAGE = "Пора приступить к чтению!"
        private const val FOREGROUND_MESSAGE_ID = 1000
        private const val ALARM_MESSAGE_ID = 1001
        private const val MAX_RELAX_TIME_MS = 60000L //todo Увеличить до 10 минут (добавить один нолик). Пока для теста время стоит 1 мин
    }
}
