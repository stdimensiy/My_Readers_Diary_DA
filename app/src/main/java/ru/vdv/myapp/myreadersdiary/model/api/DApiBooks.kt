package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.*
import ru.vdv.myapp.myreadersdiary.domain.Book

/**
## [Dad Approves API Docs / BOOKS](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
### Интерфейс компоненты BOOKS (Книги)
Реализуемые методы:
- **@GET**      [getListOfBooks]
- **@POST**     [postBook]
- **@PATCH**    [patchBook]
- **@DELETE**   [deleteBook]
- **@HEAD**     [headBook]
- **@OPTION**   [optionsBook] (*временно снят с разработки*)
- **@HTTP**     [httpBook] (*только для разработки! В релизе должен быть исключен*)
 */
interface DApiBooks {
    /**
    ## Компонент **BOOKS**   - (Книги / списки книг)
    ### "Получить список книг зарегистрированного пользователя"
     * **GET** .../books описание -  **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param key Ключ пользователя API key (зарегистрированного приложения)
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

    /**
    ## Компонент **BOOKS**   - (Книги / списки книг)
    ### "Добавить запись (создать) книги зарегистрированного пользователя в список"
     * **@POST** .../books описание -  **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param key Ключ <code>пользователя API</code> key
     * @param book создаваемый объект (книга) записи
     * @return возвращает созданный объект *[Book]*
     * @throws NullPointerException
     **/
    @POST("books")
    fun postBook(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Body book: Book
    ): Call<Any>

    /**
    ## Компонент **BOOKS**   - (Книги / списки книг)
    ### "Внести изменения в существующую запись информации о книге"
     * **@PATCH** .../books описание -  **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param key Ключ <code>пользователя API</code> key
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param book создаваемый объект (книга) записи
     * @return возвращает созданный объект *[Book]*
     * @throws NullPointerException
     **/
    @PATCH("books")
    fun patchBook(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Body book: Book
    ): Call<Any>

    /**
    ## Компонент **BOOKS**   - (Книги / списки книг)
    ### "Удаление существующей запись информации о книге"
     * **@DELETE** .../books описание -  **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param key Ключ <code>пользователя API</code> key
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param id идентификатор удаляемой записи (книги)
     * @return возвращает удаленный объект *[Book]*
     *  *на самом деле информация о книге (запись) не удаляется сразу, а помечается "на удаление"
     *  фактическое удаление записи с пометкой "на удаление" происходит автоматически
     *  через определенный промежуток времени*
     * @throws NullPointerException

     **/
    @DELETE("books")
    fun deleteBook(
        @Header("apiKey") key: String,
        @Header("user") user: String,
        @Header("id") id: String,
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