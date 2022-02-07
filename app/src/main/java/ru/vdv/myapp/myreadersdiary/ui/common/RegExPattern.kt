package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Паттерны регулярных выражений для вализации строковых значений
 * @property SIMPLE_LOGIN       Простой логин (только буквы латинского алфавита и цифры)
 * @property SIMPLE_PASSWORD    Простой пароль (только буквы латинского алфавита и цифры)
 */
object RegExPattern {
    const val SIMPLE_LOGIN = "^[a-zA-Z0-9]+\$"
    const val SIMPLE_PASSWORD = "^[a-zA-Z0-9]+\$"
}