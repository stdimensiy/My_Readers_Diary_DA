package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.DialogReadingResultsBinding
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.ProcessReadingBookUiModel
import ru.vdv.myapp.myreadersdiary.ui.common.BaseBottomSheetDialogFragment

/**
 * Диалог отображения статистики сеанса чтения.
 */
class ReadingResultsDialog : BaseBottomSheetDialogFragment<DialogReadingResultsBinding>() {

    private var onCloseButtonPressed: (() -> Unit)? = null

    /** Установкуа листенера закрытия диалога */
    fun setOnCloseButtonPressedListener(onCloseButtonPressed: () -> Unit) {
        this.onCloseButtonPressed = onCloseButtonPressed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        renderData()
    }

    override fun onResume() {
        super.onResume()
        isCancelable = false
    }

    @Suppress("UNCHECKED_CAST")
    private fun initView() {
        binding.buttonReadingResultsClose.setOnClickListener {
            onCloseButtonPressed?.invoke()
            dismiss()
        }
    }

    private fun renderData() = with(binding) {
        arguments?.getParcelable<ProcessReadingBookUiModel>(UI_MODEL_BUNDLE_KEY)?.let { data ->
            textViewReadingResultsTime.text = String.format(
                resources.getString(R.string.text_view_reading_results_time),
                data.activeStopwatchValue
            )
            textViewReadingResultsPagesCount.text = String.format(
                resources.getString(R.string.text_view_reading_results_pages_count),
                getReadPagesCount(data)
            )
            textViewReadingResultsSpeed.text = String.format(
                resources.getString(R.string.text_view_reading_results_speed),
                getReadWordsSpeed(data),
                getReadPagesSpeed(data)
            )
        }
    }

    private fun getReadPagesCount(data: ProcessReadingBookUiModel) =
        data.newCurrentPage - data.currentPage

    private fun getReadWordsSpeed(data: ProcessReadingBookUiModel): Long =
        try {
            (getReadPagesCount(data) * data.densityWordsPerPage * NUMBER_OF_MILLISECONDS_IN_MINUTE) / data.activeStopwatchElapsingTime
        } catch (e: ArithmeticException) {
            DEFAULT_READ_SPEED_VALUE
        }

    private fun getReadPagesSpeed(data: ProcessReadingBookUiModel): Long =
        try {
            (getReadPagesCount(data) * NUMBER_OF_MILLISECONDS_IN_MINUTE) / data.activeStopwatchElapsingTime
        } catch (e: ArithmeticException) {
            DEFAULT_READ_SPEED_VALUE
        }

    companion object {
        private const val UI_MODEL_BUNDLE_KEY = "processReadingBookUiModel"
        private const val NUMBER_OF_MILLISECONDS_IN_MINUTE = 60000L
        private const val DEFAULT_READ_SPEED_VALUE = 0L

        fun newInstance(processReadingBookUiModel: ProcessReadingBookUiModel) =
            ReadingResultsDialog().apply {
                arguments = bundleOf(
                    UI_MODEL_BUNDLE_KEY to processReadingBookUiModel
                )
            }
    }
}
