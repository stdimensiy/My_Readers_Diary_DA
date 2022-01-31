package ru.vdv.myapp.myreadersdiary.ui.common

/** Состояние экрана */
sealed class ScreenUiState<T> {
    data class Success<T>(val data: T) : ScreenUiState<T>()
    data class Error<T>(val error: Throwable) : ScreenUiState<T>()
    class Loading<T> : ScreenUiState<T>()
    class Finish<T> : ScreenUiState<T>()
}
