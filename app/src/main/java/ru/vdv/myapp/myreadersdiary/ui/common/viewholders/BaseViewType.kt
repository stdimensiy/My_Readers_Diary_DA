package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

/**
 * Тип представления: (сквозная нумерация всех представлений для RV)
 * @property USUAL                     основное (обычное) представление в рамках реализации RV
 * @property AUTHOR_SEPARATOR          разделитель по признаку авторства книги
 * @property CREATOR_SEPARATOR         разделитель по признаку того, кто внес запись
 * @property GROUP_SEPARATOR           разделитель по группам
 * @property TIME_SEPARATOR            разделитель по времени (общий)
 * @property FUTER                     разделитель (подвал блока)
 * @property HEADER                    разделитель (Заголовок блока)
 * @property CONTENT                   неосновное (оригинальное) представление
 * @property UNKNOWN_TYPE              неизвестный тип (при правильной работе программы не должен быть реализован)
 */
object BaseViewType {
    const val UNKNOWN_TYPE = 0
    const val USUAL = 1
    const val SECONDARY_USUAL = 2
    const val TIME_SEPARATOR = 3
    const val CREATOR_SEPARATOR = 4
    const val AUTHOR_SEPARATOR = 5
    const val GROUP_SEPARATOR = 6
    const val USER_CARD = 7
    const val FUTER = 8
    const val HEADER = 9
    const val CONTENT = 10
}