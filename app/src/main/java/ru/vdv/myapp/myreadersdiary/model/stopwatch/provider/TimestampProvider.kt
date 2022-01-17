package ru.vdv.myapp.myreadersdiary.model.stopwatch.provider

interface TimestampProvider {
    fun getMilliseconds(): Long
}
