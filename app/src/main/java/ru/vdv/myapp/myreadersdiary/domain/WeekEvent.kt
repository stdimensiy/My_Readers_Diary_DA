package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс **WeekEvent** (DA) - это модель данных недельной активности пользователя,
 * **[Dad Approves API](https://dadapproves.ru/)**
 *
 * @property weekMonday        Совокупная активность за понедельник
 * @property weekTuesday       Совокупная активность за вторник
 * @property weekWednesday     Совокупная активность за среду
 * @property weekThursday      Совокупная активность за четверг
 * @property weekFriday        Совокупная активность за пятницу
 * @property weekSaturday      Совокупная активность за субботу
 * @property weekSunday        Совокупная активность за воскресенье
 *
 * @constructor  создает объект, содержащий информацию о совокупной НЕДЕЛЬНОЙ активности пользователя
 */

@Parcelize
data class WeekEvent(
    val weekMonday: SummaryDayEvents,
    val weekTuesday: SummaryDayEvents,
    val weekWednesday: SummaryDayEvents,
    val weekThursday: SummaryDayEvents,
    val weekFriday: SummaryDayEvents,
    val weekSaturday: SummaryDayEvents,
    val weekSunday: SummaryDayEvents,
) : Parcelable
