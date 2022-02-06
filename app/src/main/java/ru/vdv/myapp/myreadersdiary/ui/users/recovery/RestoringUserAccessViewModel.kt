package ru.vdv.myapp.myreadersdiary.ui.users.recovery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.*
import java.util.regex.Pattern

class RestoringUserAccessViewModel : BaseViewModel() {
    private val _loginForm = MutableLiveData<UserLoginFragmentState>()
    private val currentFormState = UserLoginFragmentState()
    private var currentUserLogin: String = ""
    val loginFormState: LiveData<UserLoginFragmentState> = _loginForm
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    //Реализация поиска пользователя по нику (определение принципиального наличия пользователя)
    private val _currentSearchingUser = MutableLiveData<User>().apply {
        value = null
    }
    val currentSearchingUser: LiveData<User> = _currentSearchingUser

    fun startRestoreAccess(username: String) {
        // обращаемся к репозиторию с запросом на восстановление пароля
    }

    fun isUserExists(username: String): Boolean {
        // обращаемся к репозиторию с запросом на восстановление пароля
        return false
    }

    fun loginDataChanged(username: String) {
        currentUserLogin = username
        if (!isUserNameValid(username)) {
            currentFormState.isDataValid = false
            if (!isUserNameValid(username) && username.isNotEmpty()) {
                currentFormState.usernameError = R.string.invalid_username
            } else {
                currentFormState.usernameError = null
            }
        } else {
            repository.getUserInfo(username, object : CallBack<User> {
                override fun onResult(value: User) {
                    Log.d(TAG, "Принципиальный ответ получен сравниваю $currentUserLogin c ${value.login}")
                    //_currentSearchingUser.value = value
                    if (currentUserLogin.equals(value.login, true)) {
                        Log.d(TAG, "Пользователь найден")
                        currentFormState.isDataValid = true
                        currentFormState.usernameError = null
                        _loginForm.value = currentFormState
                    } else {
                        Log.d(TAG, "Видимо ответ более не актуален: $currentUserLogin c ${value.login}")
                    }
                }
                override fun onFailure(t: Int) {
                    if (t==404){
                        Log.d(TAG, "Получен ответ сервера, что пользователь НЕ найден")
                        currentFormState.isDataValid = false
                        currentFormState.usernameError = R.string.no_user_with_username
                        _loginForm.value = currentFormState
                    }
                }
            })
            currentFormState.isDataValid = false
            currentFormState.usernameError = null
        }
        _loginForm.value = currentFormState
    }

    private fun isUserNameValid(username: String): Boolean {
        val pattern = Pattern.compile(RegExPattern.SIMPLE_LOGIN)
        val matcher = pattern.matcher(username)
        return (username.length > BaseConstants.MIN_LOGIN_LENGTH && matcher.matches())
    }
}