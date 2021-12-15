package ru.vdv.myapp.myreadersdiary.model.repository

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
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
    //модуль EVENT
    fun getEventsList(callBack: CallBack<List<Event>>)
    fun setEvent(callBack: CallBack<Event>)
    fun putEventOfStartApp(callBack: CallBack<Event>)
    fun putEventOfStopApp(callBack: CallBack<Event>)
    fun putEventOfStartReading(callBack: CallBack<Event>)
    fun putEventOfStopReading(callBack: CallBack<Event>)
    fun putEventOfChangeCurrentPage(callBack: CallBack<Event>)
    fun deleteEvent(callBack: CallBack<Event>)

}