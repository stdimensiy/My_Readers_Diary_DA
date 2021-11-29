package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.vdv.myapp.myreadersdiary.domain.Book

interface ApiService {
    /**
     * Режим: API/BOOKS      - при таком запросе запрашивается список книг пользователя
     * @param user ......... - Идентификатор пользователя, список книг которого запрашивается
     * @param key .......... - базовый ключ пользователя API key
     * @param page ......... - номер запрашиваемой страницы ( >=1 )
     * @return возвращает список книг пользователя...
     */
    @GET("books")
    fun getListOfBooks(
        @Header("api_key") key: String,
        @Header("user") user: String,
        @Header("page") page: Int,
    ): Call<List<Book>>
}