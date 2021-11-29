package ru.vdv.myapp.myreadersdiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.Book

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный фрагмент, тут размещается список книг"
    }
    val text: LiveData<String> = _text

    private val _prepareItems = MutableLiveData<List<Book>>().apply {
        value = listOf<Book>(Book("01", "Первая книга"), Book("02", "Вторая книга"))
    }
    val prepareItems: LiveData<List<Book>> = _prepareItems
}