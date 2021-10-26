package ru.vdv.myapp.myreadersdiary.ui.readingProcess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReadingProcessViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это фрагмент контроля процессса чтения"
    }
    val text: LiveData<String> = _text
}