package ru.vdv.myapp.myreadersdiary.ui.users.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.ui.common.LoginResult
import ru.vdv.myapp.myreadersdiary.ui.common.UserLoginFragmentState

class UserLoginViewModel : ViewModel() {
    private val _loginForm = MutableLiveData<UserLoginFragmentState>()
    val loginFormState: LiveData<UserLoginFragmentState> = _loginForm
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(userLogin: String, password: String) {
        //запускает процесс авторизации пользователя (в обращении к репозиторию)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = UserLoginFragmentState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = UserLoginFragmentState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = UserLoginFragmentState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
}