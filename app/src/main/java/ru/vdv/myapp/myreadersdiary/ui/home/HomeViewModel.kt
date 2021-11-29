package ru.vdv.myapp.myreadersdiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

class HomeViewModel : ViewModel() {
    private val repository = RepositoryImpl()

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный фрагмент, тут размещается список книг"
    }
    val text: LiveData<String> = _text

    private val _prepareItems = MutableLiveData<List<Book>>().apply {
        repository.getListOfBooks(object : CallBack<List<Book>> {
            override fun onResult(result: List<Book>) {
                value = result
            }
        })
    }
    val prepareItems: LiveData<List<Book>> = _prepareItems

}