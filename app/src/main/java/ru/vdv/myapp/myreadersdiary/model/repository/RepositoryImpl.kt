package ru.vdv.myapp.myreadersdiary.model.repository

import android.text.format.DateUtils
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vdv.myapp.myreadersdiary.domain.*
import ru.vdv.myapp.myreadersdiary.model.api.ApiService
import ru.vdv.myapp.myreadersdiary.model.retrofit.Common
import java.util.*

class RepositoryImpl() : Repository {
    //Заглушка  недельных суммарных активностей, пока API готовится.
    private val weekEventPlug: List<WeekEvent> = listOf(
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 9, 8), 10),
            SummaryDayEvents(Date(2021, 9, 9), 5),
            SummaryDayEvents(Date(2021, 9, 10), 0),
            SummaryDayEvents(Date(2021, 9, 11), 2),
            SummaryDayEvents(Date(2021, 10, 1), 14),
            SummaryDayEvents(Date(2021, 10, 2), 0),
            SummaryDayEvents(Date(2021, 10, 3), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 4), 10),
            SummaryDayEvents(Date(2021, 10, 5), 5),
            SummaryDayEvents(Date(2021, 10, 6), 0),
            SummaryDayEvents(Date(2021, 10, 7), 2),
            SummaryDayEvents(Date(2021, 10, 8), 14),
            SummaryDayEvents(Date(2021, 10, 9), 0),
            SummaryDayEvents(Date(2021, 10, 10), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 11), 10),
            SummaryDayEvents(Date(2021, 10, 12), 5),
            SummaryDayEvents(Date(2021, 10, 13), 0),
            SummaryDayEvents(Date(2021, 10, 14), 2),
            SummaryDayEvents(Date(2021, 10, 15), 14),
            SummaryDayEvents(Date(2021, 10, 16), 0),
            SummaryDayEvents(Date(2021, 10, 17), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 18), 10),
            SummaryDayEvents(Date(2021, 10, 19), 5),
            SummaryDayEvents(Date(2021, 10, 20), 0),
            SummaryDayEvents(Date(2021, 10, 21), 2),
            SummaryDayEvents(Date(2021, 10, 22), 14),
            SummaryDayEvents(Date(2021, 10, 23), 0),
            SummaryDayEvents(Date(2021, 10, 24), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 25), 10),
            SummaryDayEvents(Date(2021, 10, 26), 5),
            SummaryDayEvents(Date(2021, 10, 27), 0),
            SummaryDayEvents(Date(2021, 10, 28), 2),
            SummaryDayEvents(Date(2021, 10, 29), 14),
            SummaryDayEvents(Date(2021, 10, 30), 0),
            SummaryDayEvents(Date(2021, 10, 31), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 1), 7),
            SummaryDayEvents(Date(2021, 11, 2), 2),
            SummaryDayEvents(Date(2021, 11, 3), 5),
            SummaryDayEvents(Date(2021, 11, 4), 9),
            SummaryDayEvents(Date(2021, 11, 5), 0),
            SummaryDayEvents(Date(2021, 11, 6), 0),
            SummaryDayEvents(Date(2021, 11, 7), 11),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 8), 10),
            SummaryDayEvents(Date(2021, 11, 9), 5),
            SummaryDayEvents(Date(2021, 11, 10), 0),
            SummaryDayEvents(Date(2021, 11, 11), 2),
            SummaryDayEvents(Date(2021, 11, 12), 14),
            SummaryDayEvents(Date(2021, 11, 13), 0),
            SummaryDayEvents(Date(2021, 11, 14), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 15), 0),
            SummaryDayEvents(Date(2021, 11, 16), 0),
            SummaryDayEvents(Date(2021, 11, 17), 1),
            SummaryDayEvents(Date(2021, 11, 18), 5),
            SummaryDayEvents(Date(2021, 11, 19), 5),
            SummaryDayEvents(Date(2021, 11, 20), 8),
            SummaryDayEvents(Date(2021, 11, 21), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 22), 4),
            SummaryDayEvents(Date(2021, 11, 23), 0),
            SummaryDayEvents(Date(2021, 11, 24), 7),
            SummaryDayEvents(Date(2021, 11, 25), 0),
            SummaryDayEvents(Date(2021, 11, 26), 1),
            SummaryDayEvents(Date(2021, 11, 27), 0),
            SummaryDayEvents(Date(2021, 11, 28), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 29), 1),
            SummaryDayEvents(Date(2021, 11, 30), 4),
            SummaryDayEvents(Date(2021, 12, 1), 2),
            SummaryDayEvents(Date(2021, 12, 2), 11),
            SummaryDayEvents(Date(2021, 12, 3), 0),
            SummaryDayEvents(Date(2021, 12, 4), 0),
            SummaryDayEvents(Date(2021, 12, 5), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 12, 6), 0),
            SummaryDayEvents(Date(2021, 12, 7), 2),
            SummaryDayEvents(Date(2021, 12, 8), 7),
            SummaryDayEvents(Date(2021, 12, 9),2),
            SummaryDayEvents(Date(2021, 12, 10), 14),
            SummaryDayEvents(Date(2021, 12, 11), 6),
            SummaryDayEvents(Date(2021, 12, 12), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 11, 13), 7),
            SummaryDayEvents(Date(2021, 11, 14), 2),
            SummaryDayEvents(Date(2021, 12, 15), 8),
            SummaryDayEvents(Date(2021, 12, 16), 1),
            SummaryDayEvents(Date(2021, 12, 17), 0),
            SummaryDayEvents(Date(2021, 12, 18), 9),
            SummaryDayEvents(Date(2021, 12, 19), 0),
        ),
    )

    //Заглушка на период подготовки BackEnd
    private val eventPlug: List<Event> = listOf<Event>(
        Event(
            "00000001", "Добавлена новая книга", Date(123456789), Date(123456789), "addNewBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
        Event(
            "00000001", "Чтение книги", Date(123456789), Date(123456789), "readBook",
            Book(
                "000000123",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "pp123456.jpg"
            )
        ),
    )

    private
    val networkService: ApiService = Common.retrofitService

    override fun getListOfBooks(callBack: CallBack<List<Book>>) {
        networkService.getListOfBooks("123", "0.123", 1)
            .enqueue(object : Callback<List<Book>> {
                override fun onResponse(
                    call: Call<List<Book>>,
                    response: Response<List<Book>>
                ) {
                    response.body()?.let { callBack.onResult(it) }
                }

                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
    }

    override fun getUserInfo(userLogin: String, callBack: CallBack<User>) {
        Log.d("Моя проверка", "Передан параметр: $userLogin")
        networkService.getUserInfo("123-321321321", userLogin)
            .enqueue(object : Callback<User> {
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
            "123456789wertrt",
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
            "123456",
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
            "123456789wertrt",
            "test-user",
            "000000123",
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

    override fun getEventsList(num: Int, callBack: CallBack<List<Event>>) {
// Запрос подготовлен ожидает готовности API

//        networkService.getListOfEvents("123", "0.123", 1,1,10,"books")
//            .enqueue(object : Callback<List<Event>> {
//                override fun onResponse(
//                    call: Call<List<Event>>,
//                    response: Response<List<Event>>
//                ) {
//                    response.body()?.let { callBack.onResult(it) }
//                }
//
//                override fun onFailure(call: Call<List<Event>>, t: Throwable) {
//                    //TODO("Not yet implemented")
//                }
//            })
        callBack.onResult(eventPlug)
    }

    override fun getSummaryEventData(callBack: CallBack<List<WeekEvent>>) {
// Запрос подготовлен ожидает готовности API

//        networkService.getListOfEvents("123", "0.123", 1,1,10,"books")
//            .enqueue(object : Callback<List<Event>> {
//                override fun onResponse(
//                    call: Call<List<Event>>,
//                    response: Response<List<Event>>
//                ) {
//                    response.body()?.let { callBack.onResult(it) }
//                }
//
//                override fun onFailure(call: Call<List<Event>>, t: Throwable) {
//                    //TODO("Not yet implemented")
//                }
//            })
        callBack.onResult(weekEventPlug)
    }

    override fun getEventsListOfBook(num: Int, bookId: String, callBack: CallBack<List<Event>>) {
        //временный ответ пока готовится API
        callBack.onResult(eventPlug)
    }

    override fun setEvent(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun putEventOfStartApp(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun putEventOfStopApp(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun putEventOfStartReading(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun putEventOfStopReading(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun putEventOfChangeCurrentPage(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }

    override fun deleteEvent(callBack: CallBack<Event>) {
        TODO("Not yet implemented")
    }
}