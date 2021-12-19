package ru.vdv.myapp.myreadersdiary.model.repository

import android.text.format.DateUtils
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.model.api.ApiService
import ru.vdv.myapp.myreadersdiary.model.retrofit.Common
import java.util.*

class RepositoryImpl() : Repository {
    //Заглушка на период подготовки BackEnd
    val eventPlug = listOf<Event>(
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