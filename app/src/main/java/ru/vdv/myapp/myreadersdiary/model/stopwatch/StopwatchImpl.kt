package ru.vdv.myapp.myreadersdiary.model.stopwatch

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder.StopwatchStateHolder
import ru.vdv.myapp.myreadersdiary.ui.common.StopwatchMode
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs

class StopwatchImpl(private val stopwatchStateHolder: StopwatchStateHolder) : Stopwatch {

    private var executor: ExecutorService? = null
    private var callback: ((String) -> Unit)? = null
    private var alarmTime: Long? = null
    private val mainThreadHandler: Handler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var stopwatchMode: StopwatchMode = StopwatchMode.PASSED_TIME

    override fun observe(callback: (String) -> Unit) {
        this.callback = callback
    }

    override fun getElapsedTime(): Long =
        stopwatchStateHolder.getElapsedTime()

    override fun getFormattedElapsedTime(): String {
        return stopwatchStateHolder.getStringTimeRepresentation()
    }

    override fun setAlarmInterval(alarmInterval: Long) {
        this.alarmTime = getElapsedTime() + alarmInterval
    }

    override fun checkAlarm(): Boolean {
        return alarmTime?.let {
            getLeftTime() < 0
        } ?: false
    }

    override fun setMode(stopwatchMode: StopwatchMode) {
        this.stopwatchMode = stopwatchMode
    }

    override fun start() {
        executor ?: startJob()
        stopwatchStateHolder.start()
    }

    override fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    override fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun getLeftTime(): Long {
        return alarmTime?.let { alarmTime ->
            alarmTime - getElapsedTime()
        } ?: ZERO_TIME
    }

    private fun startJob() {
        executor = Executors.newSingleThreadExecutor()
        executor?.let { executor ->
            executor.execute {
                try {
                    while (!executor.isShutdown) {
                        //"Первичный" вызов этого метода до паузы нужен для более "гладкого" отображения таймера:
                        mainThreadHandler.post { callback?.invoke(getFormattedTimeForRender()) }
                        Thread.sleep(DEFAULT_DELAY_MS)
                        //"Повторный" вызов этого метода после паузы по сути является workaround'ом.
                        // Т.к. в случае остановки джоба надо, чтоб было также возвращено финальное значение таймера:
                        mainThreadHandler.post { callback?.invoke(getFormattedTimeForRender()) }
                    }
                } catch (e: Exception) {
                    Log.e(ERROR_TAG, e.message.orEmpty())
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getFormattedTimeForRender(): String {
        return when (stopwatchMode) {
            StopwatchMode.PASSED_TIME -> stopwatchStateHolder.getStringTimeRepresentation()
            StopwatchMode.LEFT_TIME -> getLeftTimeFormatted()
        }
    }

    private fun getLeftTimeFormatted(): String {
        return stopwatchStateHolder.format(abs(getLeftTime()))
    }

    private fun stopJob() {
        executor?.shutdown()
        executor = null
    }

    private fun clearValue() {
        mainThreadHandler.post {
            callback?.invoke(STOPWATCH_INITIAL_VALUE)
        }
    }

    companion object {
        private const val DEFAULT_DELAY_MS = 500L
        private const val STOPWATCH_INITIAL_VALUE = "00:00:00"
        private const val ZERO_TIME = 0L
        private const val ERROR_TAG = "StopwatchError"
    }
}
