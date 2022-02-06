package ru.vdv.myapp.myreadersdiary.ui.users.recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import ru.vdv.myapp.myreadersdiary.ui.common.LoginResult
import ru.vdv.myapp.myreadersdiary.ui.common.RegExPattern
import ru.vdv.myapp.myreadersdiary.ui.common.UserLoginFragmentState
import java.util.regex.Pattern

class RestoringUserAccessViewModel : ViewModel() {
    private val _loginForm = MutableLiveData<UserLoginFragmentState>()
    private val currentFormState = UserLoginFragmentState()
    val loginFormState: LiveData<UserLoginFragmentState> = _loginForm
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun startRestoreAccess(){
        // обращаемся к репозиторию с запросом на восстановление пароля
    }

    fun loginDataChanged(username: String, userEmail: String) {
        if (!isUserNameValid(username) || !isEmailValid(userEmail)) {
            currentFormState.isDataValid = false
            if (!isEmailValid(userEmail) && userEmail.isNotEmpty()) {
                currentFormState.passwordError = R.string.invalid_password
            } else {
                currentFormState.passwordError = null
            }
            if (!isUserNameValid(username) && username.isNotEmpty()) {
                currentFormState.usernameError = R.string.invalid_username
            } else {
                currentFormState.usernameError = null
            }
        } else {
            currentFormState.isDataValid = true
            currentFormState.usernameError = null
            currentFormState.passwordError = null
        }
        _loginForm.value = currentFormState
    }

    private fun isUserNameValid(username: String): Boolean {
        val pattern = Pattern.compile(RegExPattern.SIMPLE_LOGIN)
        val matcher = pattern.matcher(username)
        return (username.length > BaseConstants.MIN_LOGIN_LENGTH && matcher.matches())
    }

    private fun isEmailValid(password: String): Boolean {
        val pattern = Pattern.compile(RegExPattern.SIMPLE_PASSWORD)
        val matcher = pattern.matcher(password)
        return (password.length > BaseConstants.MIN_PASSWORD_LENGTH && matcher.matches())
    }
}