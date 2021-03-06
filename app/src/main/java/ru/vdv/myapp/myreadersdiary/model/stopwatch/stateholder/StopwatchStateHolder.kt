package ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder

import ru.vdv.myapp.myreadersdiary.model.stopwatch.StopwatchState

interface StopwatchStateHolder {
    fun getCurrentState(): StopwatchState
    fun getStringTimeRepresentation(): String
    fun getElapsedTime(): Long
    fun format(timestamp: Long): String
    fun start()
    fun pause()
    fun stop()
}
