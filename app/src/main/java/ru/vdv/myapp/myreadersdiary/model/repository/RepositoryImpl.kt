package ru.vdv.myapp.myreadersdiary.model.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.model.api.ApiService
import ru.vdv.myapp.myreadersdiary.model.retrofit.Common

class RepositoryImpl() : Repository {
    private val networkService: ApiService = Common.retrofitService

    override fun getListOfBooks(callBack: CallBack<List<Book>>) {
        networkService.getListOfBooks("123", "0.123", 1).enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                response.body()?.let { callBack.onResult(it) }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                //TODO("Not yet implemented")
            }
        })
    }

    override fun getUserInfo(userLogin: String, callBack: CallBack<User>) {
        Log.d("Моя проверка", "Передан параметр: $userLogin")
        networkService.getUserInfo("123-321321321",userLogin).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    //callBack.onResult(it)
                    Log.d("Моя проверка", "Результат вернулься: $it")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                //TODO("Not yet implemented")
            }

        })
    }

    override fun postBook(callBack: CallBack<Any>) {
        networkService.postBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun patchBook(callBack: CallBack<Any>) {
        networkService.patchBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun deleteBook(callBack: CallBack<Any>) {
        networkService.deleteBook(
            "test-user"
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun putBook(callBack: CallBack<Any>) {
        networkService.putBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun headBook(callBack: CallBack<Any>) {
        networkService.headBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun optionsBook(callBack: CallBack<Any>) {
        networkService.optionsBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }

    override fun httpBook(callBack: CallBack<Any>) {
        networkService.httpBook(
            "test-user",
            Book(
                "id123",
                "Тестовое наименование",
                "тестовый автор",
                "Имя тестового автора",
                "Отчество",
                ""
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response.body()?.let { callBack.onResult(true) }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("Моя проверка", "Чтото пошло не так $t")
            }
        })
    }
}