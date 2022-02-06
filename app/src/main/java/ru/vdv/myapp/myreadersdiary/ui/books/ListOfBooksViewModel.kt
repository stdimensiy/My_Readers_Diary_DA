package ru.vdv.myapp.myreadersdiary.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList

class ListOfBooksViewModel : BaseViewModel() {

    private val _prepareItems = MutableLiveData<List<ToBookList>>().apply {
        value = null
    }
    val prepareItems: LiveData<List<ToBookList>> = _prepareItems
    private val _postResult = MutableLiveData<String>()
    val postResult: LiveData<String> = _postResult

    fun fetchData(){
        repository.getListOfBooks(object : CallBack<List<Book>> {
            override fun onResult(result: List<Book>) {
                //предварительно обкаточное решение без интерактора готовим данные непостредственно
                //во VM в момент получения

                val prepList: MutableList<ToBookList> = result.toMutableList()
                _prepareItems.value = prepList
            }

            override fun onFailure(t: Int) {
                //TODO("Not yet implemented")
            }
        })
    }
}