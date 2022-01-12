package ru.vdv.myapp.myreadersdiary.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel

class MainViewModel : BaseViewModel() {

    //user
    private val _currentUser = MutableLiveData<User>().apply {
        value = null
    }
    val currentUser: LiveData<User> = _currentUser

    fun fetchCurrentUser(login: String?) {
        login?.let {
            repository.getUserInfo(it, object : CallBack<User> {
                override fun onResult(value: User) {
                    _currentUser.value = value
                }
            })
        }
    }

    //events
    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsList(30, object : CallBack<List<Event>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList

    companion object {
        private const val TAG = "Моя проверка / MainViewModel" // для логирования во время отладки
    }
}