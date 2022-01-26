package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import ru.vdv.myapp.myreadersdiary.ui.common.ToMainList

class EventsListViewModel : BaseViewModel() {
    private val _prepareEventList = MutableLiveData<List<ToMainList>>().apply {
        repository.getEventsList(30, object : CallBack<List<ToMainList>> {
            override fun onResult(result: List<ToMainList>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<ToMainList>> = _prepareEventList
}