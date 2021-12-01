package ru.vdv.myapp.myreadersdiary.model.repository

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack

interface Repository {
    fun getListOfBooks(callBack: CallBack<List<Book>>)
}