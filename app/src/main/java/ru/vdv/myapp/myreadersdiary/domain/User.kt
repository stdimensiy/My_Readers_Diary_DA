package ru.vdv.myapp.myreadersdiary.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList

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
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("wallpaper_url")
    val backgroundUrl: String
) : Parcelable, ToStatList