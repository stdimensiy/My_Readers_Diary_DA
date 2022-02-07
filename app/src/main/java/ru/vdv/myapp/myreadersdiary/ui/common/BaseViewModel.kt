package ru.vdv.myapp.myreadersdiary.ui.common

import androidx.lifecycle.ViewModel
import ru.vdv.myapp.myreadersdiary.model.repository.RepositoryImpl

abstract class BaseViewModel : ViewModel() {
    protected val TAG = "${BaseConstants.MY_TAG} / ${this.javaClass.simpleName}"
    protected val repository = RepositoryImpl()
}