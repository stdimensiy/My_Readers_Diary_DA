package ru.vdv.myapp.myreadersdiary.ui.users.recovery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.StatusCode
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.*
import java.util.regex.Pattern

class RestoringUserAccessViewModel : BaseViewModel() {
    private val _loginForm = MutableLiveData<UserLoginFragmentState>()
    private val currentFormState = UserLoginFragmentState()
    val loginFormState: LiveData<UserLoginFragmentState> = _loginForm
    private var currentUserLogin: String = ""
    fun startRestoreAccess(username: String) {
        // обращаемся к репозиторию с запросом на восстановление пароля
    }

    fun loginDataChanged(username: String) {
        currentUserLogin = username
        if (!isUserNameValid(username)) {
            currentFormState.isDataValid = false
            if (!isUserNameValid(username) && username.isNotEmpty()) {
                currentFormState.usernameError = R.string.invalid_username
            } else {
                currentFormState.usernameError = R.string.user_login_is_empty
            }
            _loginForm.value = currentFormState
        } else {
            repository.getUserInfo(username, object : CallBack<User> {
                override fun onResult(value: User) {
                    if (currentUserLogin.equals(value.login, true)) {
                        currentFormState.isDataValid = true
                        currentFormState.usernameError = null
                        _loginForm.value = currentFormState
                    }
                }
                override fun onFailure(t: Int) {
                    if (username == currentUserLogin) {
                        when (t) {
                            StatusCode.NOT_FOUND -> {
                                currentFormState.isDataValid = false
                                currentFormState.usernameError = R.string.no_user_with_username
                                _loginForm.value = currentFormState
                            }
                        }
                    }
                }
            })
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        val pattern = Pattern.compile(RegExPattern.SIMPLE_LOGIN)
        val matcher = pattern.matcher(username)
        return (username.length > BaseConstants.MIN_LOGIN_LENGTH && matcher.matches())
    }
}