package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Режим отображения таймера активности: (сквозная нумерация всех представлений для RV)
 * [USUAL] - основное (обычное) представление в рамках реализации RV
 * [TIME_SEPARATOR] - разделитель по времени
 * [UNKNOWN_TYPE] - неизвестный тип (при правильно йработе программы не должен быть реализован)
 */
object BaseViewType {
    const val UNKNOWN_TYPE = 0
    const val USUAL = 1
    const val TIME_SEPARATOR = 2
}