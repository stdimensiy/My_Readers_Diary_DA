package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Класс SummaryDayEvents (DA) - это модель данных активности пользователя, получаемая от **[Dad Approves API](https://dadapproves.ru/)**
 *
 * **[date]**                 - уникальный идентификатор конкретной книги (как записи в базе данных)
 * **[title]**              - наименование книги
 * **[producerSurname]**    - фамилия автора (фамилии авторов через запятую)
 * **[producerName]**       - имя автора (имена авторов через запятую)
 * **[producerPatronymic]** - отчество автора (отчество каждого из авторов через запятую, если есть)
 * **[bookCover]**          - url изображения обложки книги
 *
 * @constructor     создает объект, содержащий информацию о совокупной активности пользователя в разрезе одного календарного
 * дня (24 часа). В сокращенном и удобном для формирования графика виде.
 */

@Parcelize
data class SummaryDayEvents(
    @SerializedName("date")
    val date: Date,
    @SerializedName("number_of_contributions")
    val numberOfContributions: Date,
) : Parcelable
