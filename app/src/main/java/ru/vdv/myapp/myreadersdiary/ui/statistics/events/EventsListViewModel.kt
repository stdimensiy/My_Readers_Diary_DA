package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel

class EventsListViewModel : BaseViewModel() {
    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsList(30, object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}