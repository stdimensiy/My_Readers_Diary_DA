package ru.vdv.myapp.myreadersdiary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

class MainViewModel : ViewModel() {
    private val repository = RepositoryImpl()
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
        "Неизвестный",
        "https://dadapproves.ru/usercontent/avatars/da0000002.jpg",
        "https://dadapproves.ru/usercontent/bg/da_bg0000002.jpg"
    )
    private val _currentUser = MutableLiveData<User>().apply {
        value = prepareUser
    }
    val currentUser: LiveData<User> = _currentUser
}