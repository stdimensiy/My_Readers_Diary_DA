package ru.vdv.myapp.myreadersdiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный фрагмент, тут размещается список книг"
    }
    val text: LiveData<String> = _text
}