package ru.vdv.myapp.myreadersdiary.model.stopwatch.stateholder

import ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator.StopwatchStateCalculatorFactory

object StopwatchStateHolderFactory {
    fun createStopwatchStateHolder(): StopwatchStateHolder = StopwatchStateHolderImpl(
        stopwatchStateCalculator = StopwatchStateCalculatorFactory.createStopwatchStateCalculator()
    )
}
