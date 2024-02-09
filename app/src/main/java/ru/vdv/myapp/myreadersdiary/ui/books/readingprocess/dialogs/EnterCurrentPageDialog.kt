package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.os.HandlerCompat
import androidx.core.os.bundleOf
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.DialogEnterBookmarkBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseDialogFragment
import ru.vdv.myapp.myreadersdiary.ui.common.InputFilters
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Диалог ввода номера текущей страницы
 */
class EnterCurrentPageDialog : BaseDialogFragment<DialogEnterBookmarkBinding>() {

    private var onCurrentPageEntered: ((Int) -> Unit)? = null
    private var onBackToReadingListener: (() -> Unit)? = null
    private val currentPage: Long by lazy {
        arguments?.getLong(CURRENT_PAGE_BUNDLE_KEY) ?: DEFAULT_CURRENT_PAGE_VALUE
    }
    private val pagesCount: Long by lazy {
        arguments?.getLong(PAGES_COUNT_BUNDLE_KEY) ?: DEFAULT_PAGES_COUNT_VALUE
    }
    private var debounceExecutor: ExecutorService? = null
    private val mainThreadHandler: Handler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }

    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener by lazy {
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) =
                with(binding) {
                    editTextInputLayoutEnterBookmark.apply {
                        setText(progress.toString())
                        editTextInputLayoutEnterBookmark.setSelection(
                            editTextInputLayoutEnterBookmark.length()
                        )
                    }
                    val seekBarColor: Int = when {
                        progress < currentPage -> ResourcesCompat.getColor(
                            resources,
                            R.color.red,
                            null
                        )

                        progress > currentPage -> ResourcesCompat.getColor(
                            resources,
                            R.color.green,
                            null
                        )

                        else -> ResourcesCompat.getColor(resources, R.color.color_accent, null)
                    }
                    seekBar.thumb.colorFilter =
                        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                            seekBarColor,
                            BlendModeCompat.SRC_IN
                        )
                }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    }

    override fun onStart() {
        super.onStart()
        debounceExecutor ?: startDebounceJob()
    }

    override fun onStop() {
        stopDebounceJob()
        super.onStop()
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
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(
            binding.editTextInputLayoutEnterBookmark,
            InputMethodManager.SHOW_FORCED
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            binding.editTextInputLayoutEnterBookmark.windowToken,
            0
        )
    }

    private fun startDebounceJob() {
        debounceExecutor = Executors.newSingleThreadExecutor()
        debounceExecutor?.let { executor ->
            executor.execute {
                try {
                    while (!executor.isShutdown) {
                        Thread.sleep(DEBOUNCE_DELAY_MS)
                        if (context != null) {
                            mainThreadHandler.post {
                                binding.dialogSeekBar.setProgress(
                                    getNewCurrentPage() ?: DEFAULT_CURRENT_PAGE_VALUE.toInt()
                                )
                                if (getNewCurrentPage() ?: DEFAULT_CURRENT_PAGE_VALUE.toInt() > pagesCount) {
                                    binding.editTextInputLayoutEnterBookmark.setText(pagesCount.toString())
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.message.orEmpty())
                    e.printStackTrace()
                }
            }
        }
    }

    private fun stopDebounceJob() {
        debounceExecutor?.shutdown()
        debounceExecutor = null
    }

    companion object {
        private const val PAGES_COUNT_BUNDLE_KEY = "pagesCount"
        private const val CURRENT_PAGE_BUNDLE_KEY = "currentPage"
        private const val DEFAULT_PAGES_COUNT_VALUE = 1L
        private const val DEFAULT_CURRENT_PAGE_VALUE = 0L
        private const val DEBOUNCE_DELAY_MS = 1000L
        private const val TAG = "EnterCurrentPageDialog"

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
