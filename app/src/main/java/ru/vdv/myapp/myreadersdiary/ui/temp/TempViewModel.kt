package ru.vdv.myapp.myreadersdiary.ui.temp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel

class TempViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        repository.postBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    val text: LiveData<String> = _text

    fun testGet() {
        Log.d("Моя проверка", "VM / Запущен метод TestGet")
        repository.getListOfBooks(object : CallBack<List<Book>> {
            override fun onResult(value: List<Book>) {
                Log.d("Моя проверка", "VM результат: $value")
            }
        })
    }

    fun testPost() {
        Log.d("Моя проверка", "VM / Запущен метод TestPost")
        repository.postBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testPatch() {
        Log.d("Моя проверка", "VM / Запущен метод TestPatch")
        repository.patchBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testDelete() {
        Log.d("Моя проверка", "VM / Запущен метод TestDelete")
        repository.deleteBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testPut() {
        Log.d("Моя проверка", "VM / Запущен метод TestPut")
        repository.putBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testHead() {
        Log.d("Моя проверка", "VM / Запущен метод TestHead")
        repository.headBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testOptions() {
        Log.d("Моя проверка", "VM / Запущен метод TestOptions")
        repository.optionsBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

    fun testHttp() {
        Log.d("Моя проверка", "VM / Запущен метод TestHttp")
        repository.httpBook(object : CallBack<Any> {
            override fun onResult(result: Any) {
                Log.d("Моя проверка", "VM результат: $result")
            }
        })
    }

}