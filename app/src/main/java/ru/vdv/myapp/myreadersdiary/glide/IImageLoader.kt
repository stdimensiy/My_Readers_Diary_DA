package ru.vdv.myapp.myreadersdiary.glide

/**
 * Универсальный интерфейс, на случай операивной замены библиотеки работающей с изображениями
 */

interface IImageLoader<T> {
    fun loadBookCover(url: String, container: T)
}