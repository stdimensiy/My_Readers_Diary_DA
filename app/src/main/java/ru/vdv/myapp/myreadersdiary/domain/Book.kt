package ru.vdv.myapp.myreadersdiary.domain

import com.google.gson.annotations.SerializedName

/**
 * Класс DataModelFD - это модель данных, получаемых от **[Free Dictionary API](https://dictionaryapi.dev/)**
 *
 * **[id]**                 - уникальный идентификатор конкретной книги (как записи в базе данных)
 * **[title]**              - наименование книги
 * **[producerSurname]**    - фамилия автора (фамилии авторов через запятую)
 * **[producerName]**       - имя автора (имена авторов через запятую)
 * **[producerPatronymic]** - отчество автора (отчество каждого из авторов через запятую, если есть)
 * **[bookCover]**          - url изображения обложки книги
 *
 * @constructor     создает объект, содержащий информацию о книге
 */

data class Book(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("producerSurname")
    val producerSurname: String,
    @SerializedName("producerName")
    val producerName: String,
    @SerializedName("producerPatronymic")
    val producerPatronymic: String,
    @SerializedName("bookCover")
    val bookCover: String,
)
