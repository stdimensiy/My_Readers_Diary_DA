package ru.vdv.myapp.myreadersdiary.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToMainList

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

                override fun onFailure(t: Int) {
                   // TODO("Not yet implemented")
                }
            })
        }
    }

    //events
    private val _prepareEventList = MutableLiveData<List<ToMainList>>().apply {
        repository.getEventsList(30, object : CallBack<List<ToMainList>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResult(result: List<ToMainList>) {
                value = result
            }

            override fun onFailure(t: Int) {
                TODO("Not yet implemented")
            }
        })
    }
    val prepareEventList: LiveData<List<ToMainList>> = _prepareEventList
}