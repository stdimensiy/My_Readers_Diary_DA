package ru.vdv.myapp.myreadersdiary.model.repository

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.User

interface Repository {
    fun getListOfBooks(callBack: CallBack<List<Book>>)
    fun getUserInfo(userLogin: String, callBack: CallBack<User>)
    fun postBook(callBack: CallBack<Any>)
    fun patchBook(callBack: CallBack<Any>)
    fun deleteBook(callBack: CallBack<Any>)
    fun putBook(callBack: CallBack<Any>)
    fun headBook(callBack: CallBack<Any>)
    fun optionsBook(callBack: CallBack<Any>)
    fun httpBook(callBack: CallBack<Any>)

}