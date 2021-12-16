package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.*
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.User

interface ApiService {
    /**
    ## Компонент **BOOKS**   - (Книги / списки книг)
    >### Интерфейс обращения API Service **GET** .../books в документации **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param key Ключ <code>пользователя API</code> key
     * @param page номер запрашиваемой страницы ( >=1 )
     * @return возвращает список объектов *[Book]*
     * @throws NullPointerException
     **/
    @GET("books")
    fun getListOfBooks(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Header("page") page: Int,
    ): Call<List<Book>>

    //для целей тестирования реализованы 8 основных методов (REST протоколов)
    @GET("user")
    fun getUserInfo(
        @Header("apiKey") key: String,
        @Header("daUserLogin") user: String,
    ): Call<User>

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

    // модуль компоненты EVENTS
    // запрос истории событий вернет список событий авторизованного пользователя
    @GET("events")
    fun getListOfEvents(
        @Header("apiKey") key: String,
        @Header("user") user: String,

        @Query("since") since: Int, //крайний идентификатор, ответ будет содержать элементы с большим или равным значением
        @Query("until") until: Int, //крайний идентификатор, ответ будет содержать элементы с менишим или равным значением
        @Query("per_page") perPage: Int, //Заявляемое количество результатов (в пределах допустимых API)
        @Query("component") component: String, //ключ компоненты с которым будут возврящаться данные (в пределах допустимых API) (в случае отсутствия вернутся все)
    ): Call<List<Event>>

    // запись события для пользователя
    @POST("events")
    fun setEvent(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Query("component") component: String,
        @Query("type") type: String,
        @Body() event: Event, //объект события
    ): Call<Event>

    // короткая запись простых событий
    @PUT("events/{simpleevent}")
    fun putEvent(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Query("component") component: String,
        @Path("simpleevent") simpleEvent: String, //короткий мнемокод базового события
        //базовые события могут быть appstart - пользователь запустил приложение (общие для всех)
        //базовые события могут быть appstop - пользователь закрыл приложение (общие для всех)
        //базовые события могут быть readingProcessStart - пользователь запустил приложение
        //базовые события могут быть readingProcessStop - пользователь запустил приложение
        //базовые события могут быть readingProcessStart - пользователь запустил приложение
        //базовые события могут быть readingProcessStop - пользователь запустил приложение
        //базовые события могут быть read - пользователь запустил приложение
    ): Call<Event>
}