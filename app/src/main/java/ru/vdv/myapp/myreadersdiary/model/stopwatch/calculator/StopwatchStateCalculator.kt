package ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator

import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchState

interface StopwatchStateCalculator {
    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running
    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused
    fun calculateElapsedTime(state: StopwatchState.Running): Long
}
