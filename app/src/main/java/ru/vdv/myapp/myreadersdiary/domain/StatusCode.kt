package ru.vdv.myapp.myreadersdiary.domain

/**
 * Константы кодов ответа сервера
 * @property CONTINUE       (100) продолжай
 * @property OK             (200) все в порядке (ОК)
 * @property CREATED        (201) создано
 * @property NO_CONTENT     (204) нет содержимого
 * @property BAD_REQUEST    (400) неправильный, некорректный запрос
 * @property FORBIDDEN      (403) доступ запрещен (не уполномочен)
 * @property NOT_FOUND      (404) ресурс не найден (запрос выполнен ответ нулевой)
 *
 */
object StatusCode {
    const val CONTINUE = 100
    const val OK = 200
    const val CREATED = 201
    const val NO_CONTENT = 204
    const val BAD_REQUEST = 400
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
}