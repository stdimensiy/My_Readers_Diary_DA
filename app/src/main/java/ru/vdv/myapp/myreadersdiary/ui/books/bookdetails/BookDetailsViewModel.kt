package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.ShortEventForBook
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class BookDetailsViewModel : BaseViewModel() {
    // Заглушка данных на период рефакторинга репозитория
    //Заглушка на период подготовки BackEnd
    private val eventPlug: List<ShortEventForBook> = listOf<ShortEventForBook>(
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

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
    }
    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsListOfBook(30, "0000011", object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = result
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}