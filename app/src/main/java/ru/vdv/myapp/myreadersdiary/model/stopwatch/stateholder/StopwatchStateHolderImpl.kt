package ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder

import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchState
import ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator.StopwatchStateCalculator

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

    private fun format(timestamp: Long): String {
        val millisecondsFormatted = (timestamp % 1000).pad(MS_DESIRED_LENGTH)
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).pad(DEFAULT_DESIRED_LENGTH)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(DEFAULT_DESIRED_LENGTH)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes / 60).pad(DEFAULT_DESIRED_LENGTH)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, PAD_CHAR)

    companion object {
        private const val DEFAULT_DESIRED_LENGTH = 2
        private const val MS_DESIRED_LENGTH = 3
        private const val PAD_CHAR: Char = '0'
    }
}
