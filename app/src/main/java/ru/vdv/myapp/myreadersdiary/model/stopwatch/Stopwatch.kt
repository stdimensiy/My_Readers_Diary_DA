package ru.vdv.myapp.myreadersdiary.model.stopwatch

import ru.vdv.myapp.myreadersdiary.ui.common.StopwatchMode

interface Stopwatch {
    fun observe(callback: (String) -> Unit)
    fun getElapsedTime(): Long
    fun getFormattedElapsedTime(): String
    fun setAlarmInterval(alarmInterval: Long)
    fun checkAlarm(): Boolean
    fun setMode(stopwatchMode: StopwatchMode)
    fun start()
    fun pause()
    fun stop()
}
