package ru.vdv.myapp.myreadersdiary.domain

data class WeekEvent(
    val weekMonday: SummaryDayEvents,
    val weekTuesday: SummaryDayEvents,
    val weekWednesday: SummaryDayEvents,
    val weekThursday: SummaryDayEvents,
    val weekFriday: SummaryDayEvents,
    val weekSaturday: SummaryDayEvents,
    val weekSunday: SummaryDayEvents,
)
