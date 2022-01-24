package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.model.stopwatch.Stopwatch
import ru.vdv.myapp.myreadersdiary.services.stopwatch.UiModelServiceHolder
import ru.vdv.myapp.myreadersdiary.ui.common.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/** ViewModel фрагмента "Контроль чтения */
class ProcessReadingBookViewModel(
    private val book: Book?,
    private val _liveData: MutableLiveData<ScreenUiState<ProcessReadingBookUiModel>> = MutableLiveData(),
    private val state: SavedStateHandle
) : BaseViewModel() {

    private var activeStopwatch: Stopwatch? = null
    private var relaxStopwatch: Stopwatch? = null
    private var uiModelServiceHolder: UiModelServiceHolder? = null
    private var uiModel: ProcessReadingBookUiModel? = null
        set(value) {
            field = value
            value?.let { uiModelServiceHolder?.invoke(value) }
        }

    val liveData: LiveData<ScreenUiState<ProcessReadingBookUiModel>>
        get() = _liveData

    init {
        initScreenState()
    }

    fun setActiveStopwatch(activeStopwatch: Stopwatch) {
        this.activeStopwatch = activeStopwatch
        this.activeStopwatch?.observe { timer -> postActiveStopwatchData(timer) }
    }

    fun setRelaxStopwatch(relaxStopwatch: Stopwatch) {
        this.relaxStopwatch = relaxStopwatch
        this.relaxStopwatch?.observe { timer -> postRelaxStopwatchData(timer) }
    }

    fun setUiModelServiceHolder(uiModelServiceHolder: UiModelServiceHolder) {
        this.uiModelServiceHolder = uiModelServiceHolder
        uiModelServiceHolder.invoke(null)?.let { uiModel ->
            postNewSuccessState(uiModel)
        }
    }

    fun saveCurrentState() {
        state.set(UI_MODEL_KEY, uiModel)
    }

    /** Обработка нажатия на нопку запуска/возобновления/паузы */
    fun onButtonProcessReadingStartOrPauseClicked() {
        uiModel?.let { uiModelNotNull ->
            when (uiModelNotNull.startOrPauseButtonMode) {
                StartOrPauseButtonMode.START -> performStart(uiModelNotNull)
                StartOrPauseButtonMode.PAUSE -> performPause(uiModelNotNull)
                StartOrPauseButtonMode.RESUME -> performResume(uiModelNotNull)
            }
        }
    }

    /** Обработка нажатия на кнопку "Стоп"
     * Если таймер запушен не был - возвращает false
     * Иначе - true
     */
    fun onButtonProcessReadingStopClicked(): Boolean {
        return (uiModel != null && uiModel?.startOrPauseButtonMode != StartOrPauseButtonMode.START).also {
            if (it) {
                activeStopwatch?.pause()
                relaxStopwatch?.pause()
                uiModel?.copy(
                    dialog = Dialog.ENTER_CURRENT_PAGE
                )?.let { processReadingBookUiModel ->
                    postNewSuccessState(processReadingBookUiModel)
                }
            }
        }
    }

    /**
     * Обрабоока нажатия на кнопку "Изменить режим таймера чтения"
     * Режим таймера можно менять только в при запущенном таймере активности
     */
    fun onButtonChangeActiveStopwatchModeClicked() {
        uiModel?.let { uiModelNotNull ->
            if (uiModelNotNull.startOrPauseButtonMode == StartOrPauseButtonMode.PAUSE) {
                val newActiveStopwatchMode = when (uiModelNotNull.activeStopwatchMode) {
                    StopwatchMode.PASSED_TIME -> StopwatchMode.LEFT_TIME
                    StopwatchMode.LEFT_TIME -> StopwatchMode.PASSED_TIME
                }
                activeStopwatch?.setMode(newActiveStopwatchMode)
                uiModelNotNull.copy(
                    activeStopwatchMode = newActiveStopwatchMode
                ).let { processReadingBookUiModel ->
                    postNewSuccessState(processReadingBookUiModel)
                }
            }
        }
    }

    /** Обработка ввода нового номера текущей страницы */
    fun onCurrentPageEntered(currentPage: Int) {
        uiModel?.let { uiModelNotNull ->
            uiModelNotNull.copy(
                dialog = Dialog.NONE,
                newCurrentPage = currentPage.toLong()
            ).let { processReadingBookUiModel ->
                postNewSuccessState(processReadingBookUiModel)
            }
        }
        readingResultsAnalysis()
    }

    /** Обработка нажатия кнопки возврата к чтению */
    fun onBackToReadingClicked() {
        uiModel?.let { uiModelNotNull ->
            performPause(uiModelNotNull)
        }
    }

    /** Формируем начальное состояние экрана */
    private fun initScreenState() {
        val currentTime = Calendar.getInstance().time
        val screenUiModel: ScreenUiState<ProcessReadingBookUiModel> =
            if (book == null) ScreenUiState.Error(Throwable(NO_DATA_ERROR))
            else {
                with(book) {
                    (state.get(UI_MODEL_KEY)
                        ?: ProcessReadingBookUiModel(
                            title = title,
                            pagesCount = pageCount,
                            wordsCount = wordsCount,
                            densityWordsPerPage = densityWordsPerPage,
                            currentPage = currentPage,
                            currentPageFormattedDate = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(currentTime),  //это значение вероятно будет приходить с бэка
                            newCurrentPage = currentPage,
                            startOrPauseButtonMode = StartOrPauseButtonMode.START,
                            startOrPauseButtonTextAndIcon = Pair(
                                R.drawable.ic_start_reading,
                                R.string.button_process_reading_start_text
                            ),
                            activeStopwatchValue = STOPWATCH_INITIAL_VALUE,
                            activeStopwatchElapsingTime = STOPWATCH_INITIAL_VALUE_MS,
                            relaxStopwatchValue = STOPWATCH_INITIAL_VALUE,
                            isGroupProcessReadingRelaxVisible = false,
                            dialog = Dialog.NONE,
                            activeStopwatchMode = StopwatchMode.PASSED_TIME,
                            processReadingTitleResId = R.string.text_view_process_reading_active_time_title
                        )).let { processReadingBookUiModel ->
                        uiModel = processReadingBookUiModel
                        ScreenUiState.Success(processReadingBookUiModel)
                    }
                }
            }
        postScreenState(screenUiModel)
    }

    /** Для отображения успешного стейта */
    private fun postNewSuccessState(newUiModel: ProcessReadingBookUiModel) {
        uiModel = newUiModel
        postScreenState(ScreenUiState.Success(newUiModel))
    }

    /** Отображение стейта */
    private fun postScreenState(screenUiModel: ScreenUiState<ProcessReadingBookUiModel>) {
        _liveData.postValue(screenUiModel)
    }

    /** Отображение таймера активности */
    private fun postActiveStopwatchData(timer: String) {
        uiModel?.copy(
            activeStopwatchValue = timer,
            activeStopwatchElapsingTime = activeStopwatch?.getElapsedTime() ?: STOPWATCH_INITIAL_VALUE_MS,
            processReadingTitleResId = getNewProcessReadingTitleResId()
        )?.let { processReadingBookUiModel ->
            postNewSuccessState(processReadingBookUiModel)
        }
    }

    /** Отображение таймера отдыха */
    private fun postRelaxStopwatchData(timer: String) {
        uiModel?.copy(
            relaxStopwatchValue = timer
        )?.let { processReadingBookUiModel ->
            postNewSuccessState(processReadingBookUiModel)
        }
    }

    /** Нажатие на кнопку "Начать чтение" */
    private fun performStart(uiModelNotNull: ProcessReadingBookUiModel) {
        activeStopwatch?.start()
        activeStopwatch?.setAlarmInterval(MAX_ACTIVE_TIME_MS)
        uiModelNotNull.copy(
            startOrPauseButtonMode = StartOrPauseButtonMode.PAUSE,
            startOrPauseButtonTextAndIcon = Pair(
                R.drawable.ic_pause_reading,
                R.string.button_process_reading_pause_text
            ),
            isGroupProcessReadingRelaxVisible = false
        ).let { processReadingBookUiModel ->
            postNewSuccessState(processReadingBookUiModel)
        }
    }

    /** Нажатие на кнопку "Сделать паузу" */
    private fun performPause(uiModelNotNull: ProcessReadingBookUiModel) {
        activeStopwatch?.setMode(StopwatchMode.PASSED_TIME)
        activeStopwatch?.pause()
        relaxStopwatch?.stop()
        relaxStopwatch?.start()
        relaxStopwatch?.setAlarmInterval(MAX_RELAX_TIME_MS)
        uiModelNotNull.copy(
            activeStopwatchMode = StopwatchMode.PASSED_TIME,
            dialog = Dialog.NONE,
            startOrPauseButtonMode = StartOrPauseButtonMode.RESUME,
            startOrPauseButtonTextAndIcon = Pair(
                R.drawable.ic_start_reading,
                R.string.button_process_reading_resume_text
            ),
            isGroupProcessReadingRelaxVisible = true
        ).let { processReadingBookUiModel ->
            postNewSuccessState(processReadingBookUiModel)
        }
    }

    /** Нажатие на кнопку "Возобновить чтение" */
    private fun performResume(uiModelNotNull: ProcessReadingBookUiModel) {
        relaxStopwatch?.pause()
        activeStopwatch?.start()
        activeStopwatch?.setAlarmInterval(MAX_ACTIVE_TIME_MS)
        uiModelNotNull.copy(
            startOrPauseButtonMode = StartOrPauseButtonMode.PAUSE,
            startOrPauseButtonTextAndIcon = Pair(
                R.drawable.ic_pause_reading,
                R.string.button_process_reading_pause_text
            ),
            isGroupProcessReadingRelaxVisible = false
        ).let { processReadingBookUiModel ->
            postNewSuccessState(processReadingBookUiModel)
        }
    }

    /** Обработка статистики чтения */
    private fun readingResultsAnalysis() {
        postScreenState(ScreenUiState.Loading())
        val newUiModel = uiModel?.copy(
            dialog = Dialog.READING_RESULTS
        )
        //todo тут будет вызов метода репозитория для отправки данных на сервер. Но пока еще не ясно, какой именнно метод и будет ли он возвращать какие-либо результаты.
        // но пока заглушка с таймаутом для проверки корректности работы лоадера:
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            Thread.sleep(READING_RESULTS_ANALYSIS_DELAY)
            newUiModel?.let { postNewSuccessState(newUiModel) }
            executor.shutdown()
        }
    }

    @StringRes
    private fun getNewProcessReadingTitleResId(): Int {
        return when {
            uiModel?.activeStopwatchMode == StopwatchMode.LEFT_TIME && activeStopwatch?.checkAlarm() == false -> R.string.text_view_process_reading_active_time_left_title
            uiModel?.activeStopwatchMode == StopwatchMode.LEFT_TIME && activeStopwatch?.checkAlarm() == true -> R.string.text_view_process_reading_active_time_over_plan_title
            else -> R.string.text_view_process_reading_active_time_title
        }
    }

    companion object {
        private const val UI_MODEL_KEY = "uiModel"
        private const val NO_DATA_ERROR = "Ошибка. Нет данных для отображения!"
        private const val STOPWATCH_INITIAL_VALUE = "00:00:00"
        private const val STOPWATCH_INITIAL_VALUE_MS = 0L
        private const val READING_RESULTS_ANALYSIS_DELAY =
            2000L  //временная задержка, пока не появится метод на бэкенде
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val MAX_RELAX_TIME_MS = 6000L //todo Увеличить до 10 минут
        private const val MAX_ACTIVE_TIME_MS = 30000L  //todo Увеличть до 30 минут
    }
}
