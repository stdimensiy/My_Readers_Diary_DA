package ru.vdv.myapp.myreadersdiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

class HomeViewModel : ViewModel() {
    private val repository = RepositoryImpl()

    private val _prepareItems = MutableLiveData<List<Book>>().apply {
        repository.getListOfBooks(object : CallBack<List<Book>> {
            override fun onResult(result: List<Book>) {
                value = result
            }
        })
    }
    val prepareItems: LiveData<List<Book>> = _prepareItems


    private val _postResult = MutableLiveData<String>()

//    fun testPost() {
//        repository.postBook(object : CallBack<Any> {
//            override fun onResult(result: Any) {
//                _postResult.value = if (result) {
//                    "Объект отправлен"
//                } else {
//                    "Объект НЕ отправлен"
//                }
//            }
//        })
//    }

    val postResult: LiveData<String> = _postResult

}