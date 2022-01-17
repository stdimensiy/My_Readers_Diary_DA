package ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator

import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchState
import ru.vdv.myapp.myreadersdiary.model.stopwatch.provider.TimestampProvider

class StopwatchStateCalculatorImpl(
    private val timestampProvider: TimestampProvider
) : StopwatchStateCalculator {
    override fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    override fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = calculateElapsedTime(oldState)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }

    override fun calculateElapsedTime(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}
