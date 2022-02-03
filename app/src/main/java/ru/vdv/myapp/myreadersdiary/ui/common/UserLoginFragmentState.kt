package ru.vdv.myapp.myreadersdiary.ui.common

/**
 * Индикатор состояния для фрагмента авторизации пользователя
 */
data class UserLoginFragmentState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
