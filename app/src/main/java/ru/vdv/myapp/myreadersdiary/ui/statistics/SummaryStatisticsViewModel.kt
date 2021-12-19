package ru.vdv.myapp.myreadersdiary.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SummaryStatisticsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это фрагмент отображения статистики чтения"
    }
    val text: LiveData<String> = _text
}