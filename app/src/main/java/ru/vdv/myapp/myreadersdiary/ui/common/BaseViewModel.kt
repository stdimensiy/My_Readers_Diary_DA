package ru.vdv.myapp.myreadersdiary.ui.common

import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

abstract class BaseViewModel : ViewModel() {
    protected val TAG = "${BaseConstants.MY_PREFIX_LOG} / ${this.javaClass.simpleName}"
    protected val repository = RepositoryImpl()
}