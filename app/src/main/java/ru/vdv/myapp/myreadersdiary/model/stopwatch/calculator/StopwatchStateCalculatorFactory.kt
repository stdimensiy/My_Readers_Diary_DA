package ru.vdv.myapp.myreadersdiary.model.stopwatch.calculator

import ru.vdv.myapp.myreadersdiary.model.stopwatch.provider.TimestampProviderFactory

object StopwatchStateCalculatorFactory {
    fun createStopwatchStateCalculator(): StopwatchStateCalculator = StopwatchStateCalculatorImpl(
        timestampProvider = TimestampProviderFactory.createTimestampProvider()
    )
}
