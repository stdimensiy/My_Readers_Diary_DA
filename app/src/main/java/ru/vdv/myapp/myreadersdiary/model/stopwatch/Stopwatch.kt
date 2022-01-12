package ru.vdv.myapp.myreadersdiary.model.stopwatch

interface Stopwatch {
    fun observe(callback: (String) -> Unit)
    fun getElapsedTime(): Long
    fun start()
    fun pause()
    fun stop()
}