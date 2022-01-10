package ru.vdv.myapp.myreadersdiary.model.stopwatch.provider

class TimestampProviderImpl : TimestampProvider {
    override fun getMilliseconds(): Long =
        System.currentTimeMillis()
}
