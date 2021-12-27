package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Класс **ShortEventForBook** - это модель данных события, получаемых от **[Dad Approves API](https://dadapproves.ru/)**
 * без основного объекта (подразумевается, что события уже относятся только к одному объекту)
 *
 * @property id                    уникальный идентификатор логированного события пользователя
 * @property title                 общее наименование логированного события
 * @property dateTime              дата фактической фиксации события
 * @property representedDateTime   представляемая (может отличаться) дата фиксации события
 * @property type                  тип события (в идеале общий набор для всего проекта start/stop/commit)
 * @property valueOfBasicProgress  значене прогрессы основного процесса
 *
 * @constructor     создает объект, содержащий информацию о событии фиксируемом в логе достижений пользователя
 */
@Parcelize
data class ShortEventForBook(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("dateTime")
    val dateTime: Date,
    @SerializedName("represented_Date_Time")
    val representedDateTime: Date,
    @SerializedName("type")
    val type: String,
    @SerializedName("value_Of_Basic_Progress")
    val valueOfBasicProgress: Int,
) : Parcelable