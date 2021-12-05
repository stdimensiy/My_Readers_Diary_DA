package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.*
import ru.vdv.myapp.myreadersdiary.domain.Book

interface ApiService {
    /**
     * Режим: API/BOOKS      - при таком запросе запрашивается список книг пользователя
     * @param user ......... - Идентификатор пользователя, список книг которого запрашивается
     * @param key .......... - базовый ключ пользователя API key
     * @param page ......... - номер запрашиваемой страницы ( >=1 )
     * @return возвращает список книг пользователя...
     */

    //для целей тестирования реализованы 8 основных методов (REST протоколов)
    @GET("books")
    fun getListOfBooks(
        @Header("api_key") key: String,
        @Header("user") user: String,
        @Header("page") page: Int,
    ): Call<List<Book>>

    @POST("books")
    fun postBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>

    @PATCH("books")
    fun patchBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>

    @DELETE("books")
    fun deleteBook(
        @Header("dauser") user: String,
    ): Call<Any>

    @PUT("books")
    fun putBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>

    @HEAD("books")
    fun headBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>

    @OPTIONS("books")
    fun optionsBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>

    @HTTP(method = "get", path = "books", hasBody = false)
    fun httpBook(
        @Header("dauser") user: String,
        @Body book: Book
    ): Call<Any>
}