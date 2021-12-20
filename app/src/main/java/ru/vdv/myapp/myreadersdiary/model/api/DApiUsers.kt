package ru.vdv.myapp.myreadersdiary.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.vdv.myapp.myreadersdiary.domain.User

/**
## [Dad Approves API Docs / USERS](https://dadapproves.ru/docs/reference-users.php)
### Интерфейс компоненты USERS (Пользователи)
Реализуемые методы:
- **@GET**      [getUserInfo]
- Будет намного больше, остальные в разработке
 */
interface DApiUsers {
    //для целей тестирования реализованы 8 основных методов (REST протоколов)
    @GET("user")
    fun getUserInfo(
        @Header("apiKey") key: String,
        @Header("daUserLogin") user: String,
    ): Call<User>
}