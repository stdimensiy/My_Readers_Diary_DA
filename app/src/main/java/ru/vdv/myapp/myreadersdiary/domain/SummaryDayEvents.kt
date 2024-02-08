package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Класс **SummaryDayEvents** (DA) - это модель данных совокупной ежедневной активности пользователя,
 * **[Dad Approves API](https://dadapproves.ru/)**
 *
 * @property date                  дата событий (даже если событий не было дата должна быть)
 * @property numberOfContributions суммарное количество событий зафиксированных системой на указанную дату
 * @constructor     создает объект, содержащий информацию о совокупной активности пользователя в
 * разрезе одного календарного дня (24 часа). В сокращенном и удобном для формирования графика виде.
 */

@Parcelize
data class SummaryDayEvents(
    @SerializedName("date")
    val date: Date,
    @SerializedName("number_of_contributions")
    val numberOfContributions: Int,
) : Parcelable
