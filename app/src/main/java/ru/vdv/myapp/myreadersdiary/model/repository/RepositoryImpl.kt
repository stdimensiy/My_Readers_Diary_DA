package ru.vdv.myapp.myreadersdiary.model.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vdv.myapp.myreadersdiary.domain.*
import ru.vdv.myapp.myreadersdiary.model.api.ApiService
import ru.vdv.myapp.myreadersdiary.model.retrofit.Common
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt

class RepositoryImpl : Repository {

    //Заглушка недельных суммарных активностей (с пустыми значениями активности), пока API готовится.
    /**
     * Формирует список заполненных объектов WeekEvent с понедельника, предшествующего [startData] по текущую дату
     * numberOfContributions в SummaryDayEvents заполняется значением = 0
     * @param startData - начальная дата, начиная с которой формировать список
     */
    override fun getClearSummaryEventData(startData: Date, callBack: CallBack<List<WeekEvent>>) {
        val endDate = Date()
        val currentCalendarDate = getBeginCalendar(startData)

        val weekEvent: MutableList<WeekEvent> = mutableListOf()
        while (currentCalendarDate.time.before(endDate)) {
            weekEvent.add(
                WeekEvent(
                    weekMonday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekTuesday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekWednesday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekThursday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekFriday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekSaturday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    ),
                    weekSunday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0
                    )
                )
            )
        }
        callBack.onResult(weekEvent)
    }

    //Заглушка недельных суммарных активностей (с рандомными значениями активности), пока API готовится.
    /**
     * Формирует список заполненных объектов WeekEvent с понедельника, предшествующего [startData] по текущую дату
     * В отличие от [getClearSummaryEventData] заполняет numberOfContributions случайным значением от 0 до 20 (включительно)
     * @param startData - начальная дата, начиная с которой формировать список
     */
    override fun getRandomSummaryEventData(startData: Date, callBack: CallBack<List<WeekEvent>>) {
        val endDate = Date()
        val currentCalendarDate = getBeginCalendar(startData)

        val weekEvent: MutableList<WeekEvent> = mutableListOf()
        while (currentCalendarDate.time.before(endDate)) {
            weekEvent.add(
                WeekEvent(
                    weekMonday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekTuesday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekWednesday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekThursday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekFriday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekSaturday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    ),
                    weekSunday = SummaryDayEvents(
                        date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time,
                        nextInt(0, 21)
                    )
                )
            )
        }
        callBack.onResult(weekEvent)
    }

    /**
     * Возвращает Calendar с датой, установленной на воскресенье недели, предшествующей указанной в [startData]
     */
    private fun getBeginCalendar(startData: Date): Calendar =
        Calendar.getInstance().apply {
            time = startData
            get(Calendar.DAY_OF_WEEK).also { dayOfWeak ->
                val dayOfWeakNormalized =
                    (7 + dayOfWeak - 2) % 7 //Изначально get возвращает как 1 - воскресенье, 7 - суббота. Приводим к виду 0 - понедельник, 6 - воскресенье
                roll(Calendar.DATE, -dayOfWeakNormalized - 1)
            }
        }

    //Заглушка на период подготовки BackEnd
    private val eventPlug: List<Event> = listOf(
        Event(
            "00000001",
            "Чтение/прогрес +10 (31 из 406)",
            Date(convertDateToLong("2021.12.24 14:25")),
            Date(convertDateToLong("2021.12.24 14:50")),
            "readProgress",
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            )
        ),
        Event(
            "00000025",
            "Добавлена новая книга (465с.) / запланирована к прочтению",
            Date(convertDateToLong("2021.12.23 14:25")),
            Date(convertDateToLong("2021.12.23 14:25")),
            "createBook",
            Book(
                "0000000038",
                "Чистый код. Создание, анализ, рефакторинг",
                "Мартин",
                "Роберт",
                "С",
                "https://dadapproves.ru/usercontent/book/covers/00000038.jpg"
            )
        ),
        Event(
            "000000123",
            "Чтение/прогрес +12 (73 из 577)",
            Date(convertDateToLong("2021.12.23 12:25")),
            Date(convertDateToLong("2021.12.23 12:25")),
            "readBook",
            Book(
                "0000000039",
                "PHP Объекты, шаблоны и методики программирования (4-е издание)",
                "Зандстра",
                "Мэтт",
                "",
                "https://dadapproves.ru/usercontent/book/covers/00000039.jpg"
            )
        ),
        Event(
            "0000000037",
            "Чтение/прогрес +6 (120 из 1070)",
            Date(convertDateToLong("2021.12.23 10:25")),
            Date(convertDateToLong("2021.12.23 10:25")),
            "readBook",
            Book(
                "0000000040",
                "Android. Программирование для профессионалов. 4-е издание.",
                "Марсикано, Гарднер, Филлипс, Стюарт",
                "...",
                "",
                "https://dadapproves.ru/usercontent/book/covers/00000040.jpg"
            )
        ),
        Event(
            "0000000039", "Чтение/прогрес +10 (123 из 434)",
            Date(convertDateToLong("2021.12.22 17:25")),
            Date(convertDateToLong("2021.12.22 17:25")),
            "readBook",
            Book(
                "0000000041",
                "Kotlin. Программирование на примерах",
                "Аделакан",
                "Ияну",
                "",
                "https://dadapproves.ru/usercontent/book/covers/00000041.jpg"
            )
        ),
        Event(
            "0000000045", "Чтение/прогрес +12 (170 из 1170)",
            Date(convertDateToLong("2021.12.22 17:25")),
            Date(convertDateToLong("2021.12.22 17:25")),
            "readBook",
            Book(
                "0000000042",
                "Философия java",
                "Эккель",
                "Брюс",
                "",
                "https://dadapproves.ru/usercontent/book/covers/000000043.jpg"
            )
        ),
        Event(
            "0000000049", "Чтение/прогрес +21 (260 из 304)",
            Date(convertDateToLong("2021.12.22 17:25")),
            Date(convertDateToLong("2021.12.22 17:25")),
            "readBook",
            Book(
                "0000000043",
                "Как устроен JavaScript",
                "Кроксфорд",
                "Дуглас",
                "",
                "https://dadapproves.ru/usercontent/book/covers/000000044.jpg"
            )
        ),
        Event(
            "0000000037", "Чтение/прогрес +14 (102 из 368)",
            Date(convertDateToLong("2021.12.22 17:25")),
            Date(convertDateToLong("2021.12.22 17:25")),
            "readBook",
            Book(
                "0000000044",
                "Приемы объектно-ориентированного программирования",
                "Гамма, Хелм, Джонсон, Влиссидес",
                "...",
                "",
                "https://dadapproves.ru/usercontent/book/covers/000000045.jpg"
            )
        ),
    )

    // Заглушка данных активности по конкретной книге на период рефакторинга репозитория
    private val shortEventForBookPlug: List<ShortEventForBook> = listOf(
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +7 (240 из 406)",
            Date(convertDateToLong("2021.12.26 14:25")),
            Date(convertDateToLong("2021.12.26 14:50")),
            "readProgress",
            7
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +11 (233 из 406)",
            Date(convertDateToLong("2021.12.25 14:25")),
            Date(convertDateToLong("2021.12.25 14:50")),
            "readProgress",
            11
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +16 (222 из 406)",
            Date(convertDateToLong("2021.12.24 14:25")),
            Date(convertDateToLong("2021.12.24 14:50")),
            "readProgress",
            16
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +6 (206 из 406)",
            Date(convertDateToLong("2021.12.24 14:25")),
            Date(convertDateToLong("2021.12.24 14:50")),
            "readProgress",
            6
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +8 (200 из 406)",
            Date(convertDateToLong("2021.12.22 14:25")),
            Date(convertDateToLong("2021.12.22 14:50")),
            "readProgress",
            8
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +12 (192 из 406)",
            Date(convertDateToLong("2021.12.21 14:25")),
            Date(convertDateToLong("2021.12.21 14:50")),
            "readProgress",
            12
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +11 (180 из 406)",
            Date(convertDateToLong("2021.12.20 14:25")),
            Date(convertDateToLong("2021.12.20 14:50")),
            "readProgress",
            11
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +20 (169 из 406)",
            Date(convertDateToLong("2021.12.19 14:25")),
            Date(convertDateToLong("2021.12.19 14:50")),
            "readProgress",
            20
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +9 (149 из 406)",
            Date(convertDateToLong("2021.12.17 14:25")),
            Date(convertDateToLong("2021.12.17 14:50")),
            "readProgress",
            9
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +14 (140 из 406)",
            Date(convertDateToLong("2021.12.16 14:25")),
            Date(convertDateToLong("2021.12.16 14:50")),
            "readProgress",
            14
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +8 (126 из 406)",
            Date(convertDateToLong("2021.12.15 14:25")),
            Date(convertDateToLong("2021.12.15 14:50")),
            "readProgress",
            8
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +19 (118 из 406)",
            Date(convertDateToLong("2021.12.14 14:25")),
            Date(convertDateToLong("2021.12.14 14:50")),
            "readProgress",
            19
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +9 (107 из 406)",
            Date(convertDateToLong("2021.12.13 14:25")),
            Date(convertDateToLong("2021.12.13 14:50")),
            "readProgress",
            9
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +13 (98 из 406)",
            Date(convertDateToLong("2021.12.11 19:25")),
            Date(convertDateToLong("2021.12.11 19:50")),
            "readProgress",
            13
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +3 (85 из 406)",
            Date(convertDateToLong("2021.12.10 19:25")),
            Date(convertDateToLong("2021.12.10 19:50")),
            "readProgress",
            3
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +14 (82 из 406)",
            Date(convertDateToLong("2021.12.09 18:25")),
            Date(convertDateToLong("2021.12.09 18:50")),
            "readProgress",
            14
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +4 (68 из 406)",
            Date(convertDateToLong("2021.12.08 13:25")),
            Date(convertDateToLong("2021.12.08 13:50")),
            "readProgress",
            4
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +5 (64 из 406)",
            Date(convertDateToLong("2021.12.07 14:25")),
            Date(convertDateToLong("2021.12.07 14:50")),
            "readProgress",
            5
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +15 (59 из 406)",
            Date(convertDateToLong("2021.12.05 14:25")),
            Date(convertDateToLong("2021.12.05 14:50")),
            "readProgress",
            15
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +16 (44 из 406)",
            Date(convertDateToLong("2021.12.04 17:25")),
            Date(convertDateToLong("2021.12.04 17:50")),
            "readProgress",
            16
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +10 (28 из 406)",
            Date(convertDateToLong("2021.12.03 16:25")),
            Date(convertDateToLong("2021.12.03 16:50")),
            "readProgress",
            10
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +12 (18 из 406)",
            Date(convertDateToLong("2021.12.02 18:25")),
            Date(convertDateToLong("2021.12.02 18:50")),
            "readProgress",
            12
        ),
        ShortEventForBook(
            "00000001",
            "Чтение/прогрес +6 (6 из 406)",
            Date(convertDateToLong("2021.12.01 18:25")),
            Date(convertDateToLong("2021.12.01 18:50")),
            "readProgress",
            6
        ),
        ShortEventForBook(
            "00000001",
            "Обновлен статус (книга назначена к прочтению)",
            Date(convertDateToLong("2021.12.01 15:30")),
            Date(convertDateToLong("2021.12.01 15:30")),
            "readProgress",
            0
        ),
        ShortEventForBook(
            "00000001",
            "Обновлены данные о книге (обложка /кол-во страниц)",
            Date(convertDateToLong("2021.12.01 15:25")),
            Date(convertDateToLong("2021.12.01 15:50")),
            "readProgress",
            0
        ),
        ShortEventForBook(
            "00000001",
            "Книга добавлена (406с.)",
            Date(convertDateToLong("2021.12.01 14:25")),
            Date(convertDateToLong("2021.12.01 14:50")),
            "readProgress",
            0
        ),
    )

    private val networkService: ApiService = Common.retrofitService

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
        networkService.getUserInfo("123-321321321", userLogin)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.body()?.let {
                        callBack.onResult(it)
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
            }
        })
    }

    override fun getEventsList(num: Int, callBack: CallBack<List<Event>>) {
        //с прочтой задержкой для отработки кастомного прогрессбара
        android.os.Handler().postDelayed({callBack.onResult(eventPlug)}, 2000)
    }

    override fun getSummaryEventData(callBack: CallBack<List<WeekEvent>>) {
        //TODO("Not yet implemented")
    }

    override fun getEventsListOfBook(
        num: Int,
        bookId: String,
        callBack: CallBack<List<ShortEventForBook>>
    ) {
        //временный ответ пока готовится API
        //с прочтой задержкой для отработки кастомного прогрессбара
        android.os.Handler().postDelayed({ callBack.onResult(shortEventForBookPlug)}, 2000)
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

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
    }
}