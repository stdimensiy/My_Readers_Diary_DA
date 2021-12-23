package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Класс **Event** - это модель данных события, получаемых от **[Dad Approves API](https://dadapproves.ru/)**
 *
 * @property id                  уникальный идентификатор логированного события пользователя
 * @property title               общее наименование логированного события
 * @property dateTime            дата фактической фиксации события
 * @property representedDateTime представляемая (может отличаться) дата фиксации события
 * @property type                тип события (в идеале общий набор для всего проекта start/stop/commit)
 * @property baseObject          базовый объект по которому фиксируется событие (в данном приложени это книга)
 *
 * @constructor     создает объект, содержащий информацию о событии фиксируемом в логе достижений пользователя
 */
@Parcelize
data class Event(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("dateTime")
    val dateTime: Date,
    @SerializedName("representedDateTime")
    val representedDateTime: Date,
    @SerializedName("type")
    val type: String,
    @SerializedName("baseObject")
    val baseObject: Book,
) : Parcelable
