package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.ProcessReadingBookFragmentBinding
import ru.vdv.myapp.myreadersdiary.services.stopwatch.StopwatchService
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs.EnterCurrentPageDialog
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs.ReadingResultsDialog
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import ru.vdv.myapp.myreadersdiary.ui.common.Dialog
import ru.vdv.myapp.myreadersdiary.ui.common.ScreenUiState

/**
 * Фрагмент "Контроль чтения"
 */
class ProcessReadingBookFragment : BaseFragment<ProcessReadingBookFragmentBinding>() {
    private val viewModel: ProcessReadingBookViewModel by viewModels {
        ProcessReadingBookViewModelFactory(
            book = arguments?.getParcelable(BOOK_ARG_KEY),
            owner = this
        )
    }
    private var stopwatchServiceBinder: StopwatchService.StopwatchServiceBinder? = null
    private var isStopwatchServiceBound: Boolean = false
    private val stopwatchServiceIntent by lazy { Intent(requireActivity(), StopwatchService::class.java) }

    private val boundServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            (binder as? StopwatchService.StopwatchServiceBinder)?.apply {
                viewModel.setActiveStopwatch(getActiveStopwatch())
                viewModel.setRelaxStopwatch(getRelaxStopwatch())
            }
            isStopwatchServiceBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            isStopwatchServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannels()
        requireActivity().startService(stopwatchServiceIntent)
    }

    //Если фрагменты будут типовыми, то onViewCreated можно вынести в BaseFragment.
    // И в BaseFragment определить абстракнтые методы observeToLiveData() и initViews()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        bindService()
    }

    override fun onStop() {
        super.onStop()
        unbindService()
    }

    override fun onDestroy() {
        requireActivity().stopService(stopwatchServiceIntent)
        super.onDestroy()
    }

    //Если все ViewModels будут с поддержкой SavedStateHandle, то тоже можно будет вынести в BaseFragment
    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveCurrentState()
        super.onSaveInstanceState(outState)
    }

    private fun initViews() {
        with(binding) {
            buttonProcessReadingStartOrPause.setOnClickListener { viewModel.onButtonProcessReadingStartOrPauseClicked() }
            buttonProcessReadingStop.setOnClickListener { viewModel.onButtonProcessReadingStopClicked() }
        }
    }

    private fun observeToLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) { screenState ->
            render(screenState)
        }
    }

    // ### rendering data area ###
    private fun render(screenUiState: ScreenUiState<ProcessReadingBookUiModel>) {
        when (screenUiState) {
            is ScreenUiState.Error -> renderErrorState(screenUiState.error)
            is ScreenUiState.Loading -> renderLoadingState()
            is ScreenUiState.Success -> renderSuccessState(screenUiState.data)
        }
    }

    private fun renderErrorState(error: Throwable) = with(binding) {
        loaderProcessReading.loadingSheet.visibility = View.GONE
        errorProcessReading.errorSheet.visibility = View.VISIBLE
        buttonProcessReadingStartOrPause.isClickable = false
        buttonProcessReadingStop.isClickable = false
        errorProcessReading.textViewErrorMessage.text = String.format(
            resources.getString(R.string.error_process_reading),
            error.message
        )
    }

    private fun renderLoadingState() = with(binding) {
        loaderProcessReading.loadingSheet.visibility = View.VISIBLE
        errorProcessReading.errorSheet.visibility = View.GONE
        buttonProcessReadingStartOrPause.isClickable = false
        buttonProcessReadingStop.isClickable = false
    }

    private fun renderSuccessState(data: ProcessReadingBookUiModel) = with(binding) {
        loaderProcessReading.loadingSheet.visibility = View.GONE
        errorProcessReading.errorSheet.visibility = View.GONE
        buttonProcessReadingStartOrPause.isClickable = true
        buttonProcessReadingStop.isClickable = true
        renderDialog(data)
        renderUiData(data)
    }

    private fun renderDialog(data: ProcessReadingBookUiModel) {
        when (data.dialog) {
            Dialog.NONE -> {}
            Dialog.ENTER_CURRENT_PAGE -> openEnterCurrentPageDialog(data)
            Dialog.READING_RESULTS -> openReadingResultsDialog(data)
        }
    }

    private fun openEnterCurrentPageDialog(data: ProcessReadingBookUiModel) {
        val enterCurrentPageDialog: EnterCurrentPageDialog =
            childFragmentManager.findFragmentByTag(CURRENT_PAGE_DIALOG_TAG) as? EnterCurrentPageDialog
                ?: EnterCurrentPageDialog
                    .newInstance(
                        pagesCount = data.pagesCount,
                        currentPage = data.currentPage
                    )
        enterCurrentPageDialog.setOnCurrentPageEnteredListener(viewModel::onCurrentPageEntered)
        if (enterCurrentPageDialog.dialog == null) enterCurrentPageDialog.show(
            childFragmentManager,
            CURRENT_PAGE_DIALOG_TAG
        )
    }

    private fun openReadingResultsDialog(data: ProcessReadingBookUiModel) {
        val readingResultsDialog: ReadingResultsDialog =
            childFragmentManager.findFragmentByTag(READING_RESULTS_DIALOG_TAG) as? ReadingResultsDialog
                ?: ReadingResultsDialog.newInstance(processReadingBookUiModel = data)

        readingResultsDialog.setOnCloseButtonPressedListener { findNavController().popBackStack() }
        if (readingResultsDialog.dialog == null) readingResultsDialog.show(
            childFragmentManager,
            READING_RESULTS_DIALOG_TAG
        )
    }

    private fun renderUiData(data: ProcessReadingBookUiModel) {
        with(binding) {
            textViewProcessReadingBookTitle.setIfChanged(data.title)
            textViewProcessReadingBookAuthors.setIfChanged(data.authors)
            textViewProcessReadingBookPagesCount.setIfChanged(
                R.string.text_view_process_reading_pages_count,
                data.pagesCount
            )
            textViewProcessReadingBookWordsCount.setIfChanged(
                R.string.text_view_process_reading_book_words_count,
                data.wordsCount
            )
            textViewProcessReadingBookDensityWords.setIfChanged(
                R.string.text_view_process_reading_book_density_words,
                data.densityWordsPerPage
            )
            textViewProcessReadingBookCurrentPage.setIfChanged(
                R.string.text_view_process_reading_book_current_page,
                data.currentPage
            )
            imageViewProcessReadingBookCover.loadImageIfEmpty(data.coverUrl)
            data.startOrPauseButtonTextAndIcon.let {
                buttonProcessReadingStartOrPause.setTextAndIconIfChanged(
                    it.first,
                    it.second
                )
            }
            textViewProcessReadingActiveStopwatch.setIfChanged(data.activeStopwatchValue)
            textViewProcessReadingRelaxStopwatch.setIfChanged(data.relaxStopwatchValue)
            groupProcessReadingRelax.setVisibilityAnimated(data.isGroupProcessReadingRelaxVisible)
        }
    }
    // ### end rendering data area ###

    // ### service area ###
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            NotificationChannel(
                NOTIFICATION_FOREGROUND_CHANNEL_ID,
                NOTIFICATION_FOREGROUND_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).let { channel -> notificationManager.createNotificationChannel(channel) }
            NotificationChannel(
                NOTIFICATION_ALARM_CHANNEL_ID,
                NOTIFICATION_ALARM_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).let { channel -> notificationManager.createNotificationChannel(channel) }
            //NOTIFICATION_ALARM_CHANNEL_ID
        }
    }

    private fun bindService() {
        val intent = Intent(requireActivity(), StopwatchService::class.java)
        requireActivity().bindService(intent, boundServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        if (isStopwatchServiceBound) {
            requireActivity().unbindService(boundServiceConnection)
            stopwatchServiceBinder = null
        }
    }
    // ### end service area ###


    // ### extensions utils area ###
    //Выполняем диффинг перед обновлением данных
    private fun TextView.setIfChanged(data: String) {
        if (text.equals(data).not()) text = data
        else return
    }

    private fun TextView.setIfChanged(@StringRes stringResId: Int, data: Long) {
        String.format(
            resources.getString(stringResId),
            data
        ).let { resultString ->
            if (text.equals(resultString).not()) text = resultString
            else return
        }
    }

    private fun MaterialButton.setTextAndIconIfChanged(
        @DrawableRes iconRes: Int,
        @StringRes textRes: Int
    ) {
        val newButtonText = resources.getString(textRes)
        if (text.equals(newButtonText).not()) {
            icon = ContextCompat.getDrawable(requireContext(), iconRes)
            text = newButtonText
        } else return
    }

    private fun ImageView.loadImageIfEmpty(imageUrl: String) {
        if (drawable == null) imageLoader.loadBookCover(imageUrl, this)
        else return
    }

    private fun View.setVisibilityAnimated(isVisible: Boolean) {
        if (isInvisible != !isVisible) {
            if (!isVisible) fadeAnimation(ANIMATION_FADE_DURATION)
            else fadeAnimation(ANIMATION_APPEAR_DURATION)
            isInvisible = !isVisible
        }
    }

    private fun fadeAnimation(duration: Long) {
        TransitionManager.beginDelayedTransition(binding.root, Fade().apply {
            this.duration = duration
        })
    }
    // ### end extensions utils area ###

    companion object {
        const val NOTIFICATION_FOREGROUND_CHANNEL_ID = "StopwatchServiceForegroundChannelId"
        const val NOTIFICATION_ALARM_CHANNEL_ID = "StopwatchServiceAlarmChannelId"
        private const val NOTIFICATION_FOREGROUND_CHANNEL_NAME = "Контроль чтения - таймер"
        private const val NOTIFICATION_ALARM_CHANNEL_NAME = "Контроль чтения - уведомления"
        private const val BOOK_ARG_KEY =
            "ARG_BOOK" //такой ключ встречается много где. Следует сделать общую публичную константу
        private const val ANIMATION_FADE_DURATION = 1000L
        private const val ANIMATION_APPEAR_DURATION = 100L
        private const val CURRENT_PAGE_DIALOG_TAG = "CurrentPageDialog"
        private const val READING_RESULTS_DIALOG_TAG = "ReadingResultsDialog"
    }
}
