package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

/**
 * Тип представления: (сквозная нумерация всех представлений для RV)
 * @property USUAL                основное (обычное) представление в рамках реализации RV
 * @property AUTHOR_SEPARATOR     разделитель по признаку авторства книги
 * @property CREATOR_SEPARATOR    разделитель по признаку того, кто внес запись
 * @property GROUP_SEPARATOR      разделитель по группам
 * @property TIME_SEPARATOR       разделитель по времени
 * @property UNKNOWN_TYPE         неизвестный тип (при правильной работе программы не должен быть реализован)
 */
object BaseViewType {
    const val UNKNOWN_TYPE = 0
    const val USUAL = 1
    const val TIME_SEPARATOR = 2
    const val CREATOR_SEPARATOR = 3
    const val AUTHOR_SEPARATOR = 4
    const val GROUP_SEPARATOR = 5
}