package ru.vdv.myapp.myreadersdiary.domain

import com.google.gson.annotations.SerializedName

/**
 * Класс DataModelFD - это модель данных, получаемых от **[Free Dictionary API](https://dictionaryapi.dev/)**
 *
 * **[id]**      - уникальный идентификатор конкретной книги (как записи в базе данных)
 * **[title]**   - наименование книги
 *
 * @constructor     создает объект, содержащий информацию о книге
 */

data class Book(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
)
