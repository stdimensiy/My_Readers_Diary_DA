package ru.vdv.myapp.myreadersdiary.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

class EventsViewModel : ViewModel() {
    private val repository = RepositoryImpl()
    //events

    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsList(object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}