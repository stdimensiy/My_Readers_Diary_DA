package ru.vdv.myapp.myreadersdiary.model.repository

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack

class RepositoryImpl() : Repository {
    private val responcePlug: List<Book> =
        listOf<Book>(Book("01", "Первая книга"), Book("02", "Вторая книга"))

    override fun getListOfBooks(callBack: CallBack<List<Book>>) {
        callBack.onResult(responcePlug)
    }
}