package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess


import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import ru.vdv.myapp.myreadersdiary.ui.common.Dialog
import ru.vdv.myapp.myreadersdiary.ui.common.StartOrPauseButtonMode
import ru.vdv.myapp.myreadersdiary.ui.common.StopwatchMode

/**
 * UI-модель фрагмента ProcessReadingBookFragment
 * @param title Заголовок книги
 * @param pagesCount Количество страниц в книге
 * @param wordsCount Количество слов в книге
 * @param densityWordsPerPage Плотность слов (среднее количество слов на страницу)
 * @param currentPage Текущая страница
 * @param currentPageFormattedDate Дата установки номера текущей страницы
 * @param newCurrentPage Новое значение текушей странице (указывается по завершению сеанса чтения)
 * @param startOrPauseButtonMode Режим кнопки (Начать чтение, Сделать паузу, Продолжить чтение)
 * @param startOrPauseButtonTextAndIcon Иконка кнопки
 * @param activeStopwatchValue Значение таймера активности (форматированное)
 * @param activeStopwatchElapsingTime Значение таймера активности в мс
 * @param relaxStopwatchValue Значение таймера отдыха (форматированное)
 * @param isGroupProcessReadingRelaxVisible Отображать или нет таймер отдыха
 * @param dialog Отображаемый на экране диалог
 * @param activeStopwatchMode Режим таймиера активности
 * @param processReadingTitleResId ID строкового ресурса для заголовка над таймером активности
 */
@Parcelize
data class ProcessReadingBookUiModel(
    val title: String,
    val pagesCount: Long,
    val wordsCount: Long,
    val densityWordsPerPage: Long,
    val currentPage: Long,
    val currentPageFormattedDate: String,
    val newCurrentPage: Long,
    val startOrPauseButtonMode: StartOrPauseButtonMode,
    val startOrPauseButtonTextAndIcon: Pair<Int, Int>,
    val activeStopwatchValue: String,
    val activeStopwatchElapsingTime: Long,
    val relaxStopwatchValue: String,
    val isGroupProcessReadingRelaxVisible: Boolean,
    val dialog: Dialog,
    val activeStopwatchMode: StopwatchMode,
    @StringRes val processReadingTitleResId: Int
) : Parcelable
