package ru.vdv.myapp.myreadersdiary.model.stopwatch

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder.StopwatchStateHolder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StopwatchImpl(private val stopwatchStateHolder: StopwatchStateHolder) : Stopwatch {

    private var executor: ExecutorService? = null
    private var callback: ((String) -> Unit)? = null
    private val mainThreadHandler: Handler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }

    override fun observe(callback: (String) -> Unit) {
        this.callback = callback
    }

    override fun getElapsedTime(): Long =
        stopwatchStateHolder.getElapsedTime()


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

    private fun startJob() {
        executor = Executors.newSingleThreadExecutor()
        executor?.let { executor ->
            executor.execute {
                try {
                    while (!executor.isShutdown) {
                        mainThreadHandler.post { callback?.invoke(stopwatchStateHolder.getStringTimeRepresentation()) }
                        Thread.sleep(DEFAULT_DELAY_MS)
                    }
                } catch (e: Exception) {
                    Log.e(ERROR_TAG, e.message.orEmpty())
                    e.printStackTrace()
                }
            }
        }
    }

    private fun stopJob() {
        executor?.shutdown()
        executor = null
    }

    private fun clearValue() {
        callback?.invoke(STOPWATCH_INITIAL_VALUE)

    }

    companion object {
        private const val DEFAULT_DELAY_MS = 20L
        private const val STOPWATCH_INITIAL_VALUE = "00:00:000"
        private const val ERROR_TAG = "StopwatchError"
    }
}
