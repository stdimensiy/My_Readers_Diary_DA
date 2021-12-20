package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.*
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.Event

/**
## [Dad Approves API Docs / EVENTS](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
### Интерфейс компоненты EVENTS (События)
Реализуемые методы:
- **@GET**      [getListOfEvents]
- **@POST**     [setEvent]
- **@PUT**      [putEvent]
 */
interface DApiEvents {
    /**
    ## Компонент **EVENTS**   - (События)
    ### "Получить список событий зафиксированных пользователем"
     * **GET** .../events описание -  **[Dad Approves API Docs](https://dadapproves.ru/docs/reference-users.php#patch-user)**
     * @param key Ключ пользователя API key (зарегистрированного приложения)
     * @param user Идентификатор пользователя, список книг которого запрашивается
     * @param since Крайний идентификатор события, (*выборка будет содержать элементы с большиминдексом*)
     * @return возвращает список объектов *[Book]*
     * @throws NullPointerException
     **/
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