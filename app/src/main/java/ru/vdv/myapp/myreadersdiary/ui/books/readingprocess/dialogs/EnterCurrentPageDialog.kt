package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.os.bundleOf
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.DialogEnterBookmarkBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseDialogFragment
import ru.vdv.myapp.myreadersdiary.ui.common.InputFilters

/**
 * Диалог ввода номера текущей страницы
 */
class EnterCurrentPageDialog : BaseDialogFragment<DialogEnterBookmarkBinding>() {

    private var onCurrentPageEntered: ((Int) -> Unit)? = null
    private var onBackToReadingListener: (() -> Unit)? = null
    private val currentPage: Long by lazy { arguments?.getLong(CURRENT_PAGE_BUNDLE_KEY) ?: DEFAULT_CURRENT_PAGE_VALUE }
    private val pagesCount: Long by lazy { arguments?.getLong(PAGES_COUNT_BUNDLE_KEY) ?: DEFAULT_PAGES_COUNT_VALUE }

    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener by lazy {
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.editTextInputLayoutEnterBookmark.setText(progress.toString())
                val seekBarColor: Int = when {
                    progress < currentPage -> ResourcesCompat.getColor(resources, R.color.red, null)
                    progress > currentPage -> ResourcesCompat.getColor(resources, R.color.green, null)
                    else -> ResourcesCompat.getColor(resources, R.color.color_accent, null)
                }
                seekBar.thumb.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(seekBarColor, BlendModeCompat.SRC_IN)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    }

    private val textWatcher: TextWatcher by lazy {
        object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) = with(binding) {
                editTextInputLayoutEnterBookmark.setSelection(editTextInputLayoutEnterBookmark.length())
                /* Обновление SeekBar при ручном изменении текста пока не реализовано.
                * т.к. может произойти зацикливание (при изменении SeekBar также меняется значение в EditText) и зависание приложения.
                * При использовании корутин или Rx можно было бы воспользоваться debounce и т.п.
                */
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        }
    }

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
        binding.editTextInputLayoutEnterBookmark.post { showKeyboard() }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViews() = with(binding) {
        buttonEnterBookmark.setOnClickListener { validateCurrentPage() }
        buttonBackToReading.setOnClickListener { backToReadingClicked() }
        editTextInputLayoutEnterBookmark.apply {
            setText(currentPage.toString())
            filters += arrayOf(InputFilters.digitsFilter)
        }
        dialogSeekBar.setMaxValue(pagesCount.toInt())
        dialogSeekBar.setProgress(currentPage.toInt())
        dialogSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        editTextInputLayoutEnterBookmark.addTextChangedListener(textWatcher)
    }

    private fun validateCurrentPage() = with(binding) {
        val newCurrentPage = getNewCurrentPage()
        when {
            newCurrentPage == null ->
                inputLayoutEnterBookmark.error =
                    resources.getString(R.string.dialog_process_reading_error_is_empty)
            newCurrentPage > pagesCount ->
                inputLayoutEnterBookmark.error =
                    resources.getString(R.string.dialog_process_reading_error_more_than_pages_count)
            else -> {
                inputLayoutEnterBookmark.error = null
                onCurrentPageEntered?.invoke(newCurrentPage)
                hideKeyboard()
                dismiss()
            }
        }
    }

    private fun backToReadingClicked() {
        onBackToReadingListener?.invoke()
        hideKeyboard()
        dismiss()
    }

    private fun getNewCurrentPage(): Int? =
        try {
            binding.editTextInputLayoutEnterBookmark.text.toString().toInt()
        } catch (e: NumberFormatException) {
            null
        }

    private fun showKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.editTextInputLayoutEnterBookmark, InputMethodManager.SHOW_FORCED)
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.editTextInputLayoutEnterBookmark.windowToken, 0)
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
