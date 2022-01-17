package ru.vdv.myapp.myreadersdiary.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.*
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class SummaryStatisticsViewModel : BaseViewModel() {

    //user
    private val _currentUser = MutableLiveData<User>().apply { value = null }
    val currentUser: LiveData<User> = _currentUser

    fun fetchCurrentUser(login: String?) {
        login?.let {
            repository.getUserInfo(it, object : CallBack<User> {
                override fun onResult(value: User) {
                    _currentUser.value = value
                }
            })
        }
    }

    //events
    private val _prepareEventList = MutableLiveData<List<WeekEvent>>().apply {
        val startData = Date(convertDateToLong("2020.11.13 10:25"))
        repository.getRandomSummaryEventData(startData, object : CallBack<List<WeekEvent>> {
            override fun onResult(result: List<WeekEvent>) {
                value = result
            }
        })
    }

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
    }

    val prepareEventList: LiveData<List<WeekEvent>> = _prepareEventList
}