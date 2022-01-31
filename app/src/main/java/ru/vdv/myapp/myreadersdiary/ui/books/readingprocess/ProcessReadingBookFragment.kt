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
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.services.stopwatch.StopwatchService
import ru.vdv.myapp.myreadersdiary.ui.CustomBackButtonListener
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs.EnterCurrentPageDialog
import ru.vdv.myapp.myreadersdiary.ui.books.readingprocess.dialogs.ReadingResultsDialog
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import ru.vdv.myapp.myreadersdiary.ui.common.Dialog
import ru.vdv.myapp.myreadersdiary.ui.common.ScreenUiState

/**
 * Фрагмент "Контроль чтения"
 */
class ProcessReadingBookFragment : BaseFragment<ProcessReadingBookFragmentBinding>(),
    CustomBackButtonListener {

    private val book: Book? by lazy { arguments?.getParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY) }
    private val viewModel: ProcessReadingBookViewModel by viewModels {
        ProcessReadingBookViewModelFactory(
            book = book,
            owner = this
        )
    }
    private var isStopwatchServiceBound: Boolean = false
        set(value) {
            field = value
            stopwatchService?.setBoundState(isStopwatchServiceBound)
        }
    private var stopwatchService: StopwatchService.StopwatchServiceBinder? = null
    private val stopwatchServiceIntent by lazy {
        Intent(
            requireActivity(),
            StopwatchService::class.java
        )
    }

    private val boundServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            stopwatchService = binder as? StopwatchService.StopwatchServiceBinder
            stopwatchService?.apply {
                viewModel.setActiveStopwatch(getActiveStopwatch())
                viewModel.setRelaxStopwatch(getRelaxStopwatch())
                viewModel.setUiModelServiceHolder(uiModelServiceHolder)
                setCurrentBook(book)
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

    override fun onDestroy() {
        unbindService()
        super.onDestroy()
    }

    override fun backPressed(): Boolean {
        return viewModel.onButtonProcessReadingStopClicked()
    }

    //Если все ViewModels будут с поддержкой SavedStateHandle, то тоже можно будет вынести в BaseFragment
    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveCurrentState()
        super.onSaveInstanceState(outState)
    }

    private fun initViews() = with(binding) {
        buttonProcessReadingStartOrPause.setOnClickListener { viewModel.onButtonProcessReadingStartOrPauseClicked() }
        buttonProcessReadingStop.setOnClickListener { viewModel.onButtonProcessReadingStopClicked() }
        buttonChangeActiveStopwatchMode.setOnClickListener { viewModel.onButtonChangeActiveStopwatchModeClicked() }
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
            is ScreenUiState.Finish -> navigateUpWithStopService()
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
        enterCurrentPageDialog.setOnBackToReadingListener { viewModel.onBackToReadingClicked() }
        if (enterCurrentPageDialog.dialog == null) enterCurrentPageDialog.show(
            childFragmentManager,
            CURRENT_PAGE_DIALOG_TAG
        )
    }

    private fun openReadingResultsDialog(data: ProcessReadingBookUiModel) {
        val readingResultsDialog: ReadingResultsDialog =
            childFragmentManager.findFragmentByTag(READING_RESULTS_DIALOG_TAG) as? ReadingResultsDialog
                ?: ReadingResultsDialog.newInstance(processReadingBookUiModel = data)

        readingResultsDialog.setOnCloseButtonPressedListener { navigateUpWithStopService() }
        if (readingResultsDialog.dialog == null) readingResultsDialog.show(
            childFragmentManager,
            READING_RESULTS_DIALOG_TAG
        )
    }

    private fun navigateUpWithStopService() {
        unbindService()
        requireActivity().stopService(stopwatchServiceIntent)
        findNavController().navigateUp()
    }

    private fun renderUiData(data: ProcessReadingBookUiModel) {
        with(binding) {
            textViewProcessReadingBookTitle.setIfChanged(data.title)
            textViewProcessReadingBookPagesCount.setIfChanged(
                R.string.text_view_process_reading_pages_count,
                data.pagesCount
            )
            textViewProcessReadingBookCurrentPage.setIfChanged(
                R.string.text_view_process_reading_book_current_page,
                data.currentPageFormattedDate,
                data.currentPage
            )
            data.startOrPauseButtonTextAndIcon.let {
                buttonProcessReadingStartOrPause.setTextAndIconIfChanged(
                    it.first,
                    it.second
                )
            }
            textViewProcessReadingActiveStopwatch.setIfChanged(data.activeStopwatchValue)
            textViewProcessReadingRelaxStopwatch.setIfChanged(data.relaxStopwatchValue)
            textViewProcessReadingActiveTimeTitle.setIfChanged(getString(data.processReadingTitleResId))
            groupProcessReadingRelax.setVisibilityAnimated(data.isGroupProcessReadingRelaxVisible)
        }
    }
    // ### end rendering data area ###

    // ### service area ###
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        }
    }

    private fun bindService() {
        val intent = Intent(requireActivity(), StopwatchService::class.java)
        requireActivity().bindService(intent, boundServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        if (isStopwatchServiceBound) {
            requireActivity().unbindService(boundServiceConnection)
            isStopwatchServiceBound = false
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

    private fun TextView.setIfChanged(@StringRes stringResId: Int, dateFormatted: String, number: Long) {
        String.format(
            resources.getString(stringResId),
            dateFormatted,
            number
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
        private const val ANIMATION_FADE_DURATION = 500L
        private const val ANIMATION_APPEAR_DURATION = 100L
        private const val CURRENT_PAGE_DIALOG_TAG = "CurrentPageDialog"
        private const val READING_RESULTS_DIALOG_TAG = "ReadingResultsDialog"
    }
}
