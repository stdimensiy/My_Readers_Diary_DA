package ru.vdv.myapp.myreadersdiary.model.stopwatch

import ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder.StopwatchStateHolderFactory

object StopwatchFactory {
    fun createStopwatch(): Stopwatch = StopwatchImpl(
        stopwatchStateHolder = StopwatchStateHolderFactory.createStopwatchStateHolder()
    )
}
