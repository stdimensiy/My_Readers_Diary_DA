package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

class BookDetailsViewModel : ViewModel() {
    private val repository = RepositoryImpl()
    //events

    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsListOfBook(30, "0000011", object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}