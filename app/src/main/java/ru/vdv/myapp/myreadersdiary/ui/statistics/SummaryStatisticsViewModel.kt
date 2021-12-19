package ru.vdv.myapp.myreadersdiary.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.*
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl
import java.text.DateFormat
import java.time.LocalDate
import java.util.*

class SummaryStatisticsViewModel : ViewModel() {
    private val repository = RepositoryImpl()
    private val prepareUser = User(
        "DarthVerteliy",
        "https://dadapproves.ru/usercontent/avatars/da0000002.jpg",
        "https://dadapproves.ru/usercontent/bg/da_bg0000002.jpg"
    )
    private val _currentUser = MutableLiveData<User>().apply {
        value = prepareUser
    }
    val currentUser: LiveData<User> = _currentUser

    //events

    private val _prepareEventList = MutableLiveData<List<WeekEvent>>().apply {
        repository.getSummaryEventData(object : CallBack<List<WeekEvent>> {
            override fun onResult(result: List<WeekEvent>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<WeekEvent>> = _prepareEventList
}