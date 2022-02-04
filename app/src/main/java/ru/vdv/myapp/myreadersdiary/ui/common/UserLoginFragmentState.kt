package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Индикатор состояния для фрагмента авторизации пользователя
 */
data class UserLoginFragmentState(
    var usernameError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = false
)
