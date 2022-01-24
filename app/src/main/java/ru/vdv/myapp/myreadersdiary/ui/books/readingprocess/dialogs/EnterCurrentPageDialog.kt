package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.DialogEnterBookmarkBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseDialogFragment

/**
 * Диалог ввода номера текущей страницы
 */
class EnterCurrentPageDialog : BaseDialogFragment<DialogEnterBookmarkBinding>() {

    private var onCurrentPageEntered: ((Int) -> Unit)? = null
    private var onBackToReadingListener: (() -> Unit)? = null
    private val currentPage: Long by lazy { arguments?.getLong(CURRENT_PAGE_BUNDLE_KEY) ?: DEFAULT_CURRENT_PAGE_VALUE }
    private val pagesCount: Long by lazy { arguments?.getLong(PAGES_COUNT_BUNDLE_KEY) ?: DEFAULT_PAGES_COUNT_VALUE }

    /** Установка листенера установки текущей страницы */
    fun setOnCurrentPageEnteredListener(onCurrentPageEntered: (Int) -> Unit) {
        this.onCurrentPageEntered = onCurrentPageEntered
    }

    /** Установка листенера кнопки возврата к чтению */
    fun setOnBackToReadingListener(onBackToReadingListener: () -> Unit) {
        this.onBackToReadingListener = onBackToReadingListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        isCancelable = false
        binding.editTextInputLayoutEnterBookmark.requestFocus()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViews() = with(binding) {
        buttonEnterBookmark.setOnClickListener { validateCurrentPage() }
        buttonBackToReading.setOnClickListener { backToReadingClicked() }
        editTextInputLayoutEnterBookmark.setText(currentPage.toString())
    }

    private fun validateCurrentPage() = with(binding) {
        val newCurrentPage = getNewCurrentPage()
        when {
            newCurrentPage == null ->
                inputLayoutEnterBookmark.error =
                    resources.getString(R.string.dialog_process_reading_error_is_empty)
            newCurrentPage < currentPage ->
                inputLayoutEnterBookmark.error =
                    resources.getString(R.string.dialog_process_reading_error_less_than_last)
            newCurrentPage > pagesCount ->
                inputLayoutEnterBookmark.error =
                    resources.getString(R.string.dialog_process_reading_error_more_than_pages_count)
            else -> {
                inputLayoutEnterBookmark.error = null
                onCurrentPageEntered?.invoke(newCurrentPage)
                dismiss()
            }
        }
    }

    private fun backToReadingClicked() {
        onBackToReadingListener?.invoke()
        dismiss()
    }

    private fun getNewCurrentPage(): Int? =
        try {
            binding.editTextInputLayoutEnterBookmark.text.toString().toInt()
        } catch (e: NumberFormatException) {
            null
        }

    companion object {
        private const val PAGES_COUNT_BUNDLE_KEY = "pagesCount"
        private const val CURRENT_PAGE_BUNDLE_KEY = "currentPage"
        private const val DEFAULT_PAGES_COUNT_VALUE = 1L
        private const val DEFAULT_CURRENT_PAGE_VALUE = 0L

        fun newInstance(
            pagesCount: Long,
            currentPage: Long
        ) = EnterCurrentPageDialog().apply {
            arguments = bundleOf(
                PAGES_COUNT_BUNDLE_KEY to pagesCount,
                CURRENT_PAGE_BUNDLE_KEY to currentPage
            )
        }
    }
}
