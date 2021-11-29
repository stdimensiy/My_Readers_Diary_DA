package ru.vdv.myapp.myreadersdiary.model.retrofit

import ru.vdv.myapp.myreadersdiary.model.api.ApiService

object Common {
    private const val BASE_URL_DA = "https://dadapproves.ru/api/"

    val retrofitService: ApiService
        get() {
            return RetrofitClientDA.getClient(BASE_URL_DA)
                .create(ApiService::class.java)
        }
}