package ru.vdv.myapp.myreadersdiary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel

class MainViewModel : BaseViewModel() {
    fun fetchCurrentUser(login: String?) {
        // в дальнейшем необходимо использовать тольео хешированный идентификатор
        // нно для начала лупим только по логину
        login?.let {
            repository.getUserInfo(it, object : CallBack<User> {
                override fun onResult(value: User) {
                    _currentUser.value = value
                }
            })
        }
    }

    private val prepareUser = User(
        "DarthVerteliy",
        "https://dadapproves.ru/usercontent/avatars/da0000002.jpg",
        "https://dadapproves.ru/usercontent/bg/da_bg0000002.jpg"
    )
    private val _currentUser = MutableLiveData<User>().apply {
        value = prepareUser
    }
    val currentUser: LiveData<User> = _currentUser

    //events

    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsList(30, object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}