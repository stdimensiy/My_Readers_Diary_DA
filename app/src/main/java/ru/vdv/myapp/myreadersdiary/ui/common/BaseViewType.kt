package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Тип представления: (сквозная нумерация всех представлений для RV)
 * @property USUAL                 основное (обычное) представление в рамках реализации RV
 * @property TIME_SEPARATOR       разделитель по времени
 * @property UNKNOWN_TYPE        неизвестный тип (при правильной работе программы не должен быть реализован)
 */
object BaseViewType {
    const val UNKNOWN_TYPE = 0
    const val USUAL = 1
    const val TIME_SEPARATOR = 2
}