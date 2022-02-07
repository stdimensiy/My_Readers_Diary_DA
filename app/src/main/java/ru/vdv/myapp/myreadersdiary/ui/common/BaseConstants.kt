package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Базовые константы
 * @property MY_TAG                   Строка для фильтра (TAG) логирования приложения, общая
 * @property RETROFIT_FAILURE         Стандартное сообщение логирования ошибки RETROFIT
 * @property MY_BOOK_BUNDLE_KEY       Ключ для бандла объекта BOOK (книги)
 * @property MIN_LOGIN_LENGTH         Ограничитель минимальной длины логина
 * @property MIN_PASSWORD_LENGTH      Ограничитель минимальной длины пароля
 */
object BaseConstants {
    const val MY_TAG = "Моя проверка"
    const val RETROFIT_FAILURE = "ОШИБКА при работе компонены RETROFIT"
    const val MY_BOOK_BUNDLE_KEY = "ARG_BOOK"
    const val MIN_LOGIN_LENGTH = 3
    const val MIN_PASSWORD_LENGTH = 3
}