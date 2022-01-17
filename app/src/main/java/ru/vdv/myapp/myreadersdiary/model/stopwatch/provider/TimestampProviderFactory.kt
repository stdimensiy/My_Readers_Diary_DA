package ru.vdv.myapp.myreadersdiary.model.stopwatch.provider

object TimestampProviderFactory {
    fun createTimestampProvider(): TimestampProvider = TimestampProviderImpl()
}
