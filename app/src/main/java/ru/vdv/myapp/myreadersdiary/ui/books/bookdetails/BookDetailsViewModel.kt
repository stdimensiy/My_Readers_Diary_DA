package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.ShortEventForBook
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel

class BookDetailsViewModel : BaseViewModel() {
    private val _prepareEventList = MutableLiveData<List<ShortEventForBook>>().apply {
        repository.getEventsListOfBook(30, "0000011", object : CallBack<List<ShortEventForBook>> {
            override fun onResult(result: List<ShortEventForBook>) {
                value = result
            }

            override fun onFailure(t: Int) {
                //TODO("Not yet implemented")
            }
        })
    }
    val prepareEventList: LiveData<List<ShortEventForBook>> = _prepareEventList
}