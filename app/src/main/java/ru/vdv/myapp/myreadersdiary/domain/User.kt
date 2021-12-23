package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Класс **User** (DA) - это модель данных пользователя, получаемых от **[Dad Approves API](https://dadapproves.ru/)**
 *
 * @property name                 Имя пользователя
 * @property avatarUrl            ссылка на аватар пользователя
 * @property backgroundUrl        ссылка на кастомное фоновое изображение профиля пользователя
 *
 * @constructor     создает объект, содержащий информацию о пользователе
 */

@Parcelize
data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("avatarUrl:")
    val avatarUrl: String,
    @SerializedName("backgroundUrl")
    val backgroundUrl: String
): Parcelable