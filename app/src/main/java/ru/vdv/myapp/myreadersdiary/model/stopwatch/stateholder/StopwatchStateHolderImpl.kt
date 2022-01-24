package ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder

import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchState
import ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator.StopwatchStateCalculator
import java.util.concurrent.TimeUnit

class StopwatchStateHolderImpl(private val stopwatchStateCalculator: StopwatchStateCalculator) : StopwatchStateHolder {
    private var currentState: StopwatchState = StopwatchState.Paused(0)

    override fun getCurrentState(): StopwatchState =
        currentState

    override fun getElapsedTime(): Long =
        when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> stopwatchStateCalculator.calculateElapsedTime(currentState)
        }

    override fun getStringTimeRepresentation(): String {
        return format(getElapsedTime())
    }

    override fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    override fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    override fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    override fun format(timestamp: Long): String {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timestamp)
        val secondsFormatted = (seconds % SECONDS_IN_MINUTE).pad(DEFAULT_DESIRED_LENGTH)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)
        val minutesFormatted = (minutes % MINUTES_IN_HOUR).pad(DEFAULT_DESIRED_LENGTH)
        val hours = TimeUnit.MINUTES.toHours(minutes)
        val hoursFormatted = hours.pad(DEFAULT_DESIRED_LENGTH)
        return "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, PAD_CHAR)

    companion object {
        private const val DEFAULT_DESIRED_LENGTH = 2
        private const val PAD_CHAR: Char = '0'
        private const val MINUTES_IN_HOUR = 60
        private const val SECONDS_IN_MINUTE = 60
    }
}