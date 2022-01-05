package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Класс Book (DA) - это модель данных, получаемых от **[Dad Approves API](https://dadapproves.ru/)**
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

@Parcelize
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
    @SerializedName("pageCount")
    val pageCount: Long = 0,
    @SerializedName("wordsCount")
    val wordsCount: Long = 0,
    @SerializedName("densityWordsPerPage")
    val densityWordsPerPage: Long = 0,
    @SerializedName("currentPage")
    val currentPage: Long = 0,
) : Parcelable
