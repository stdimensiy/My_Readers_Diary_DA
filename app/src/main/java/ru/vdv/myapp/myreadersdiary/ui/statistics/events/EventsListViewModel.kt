package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.CallBack
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class EventsListViewModel : BaseViewModel() {
    // Заглушка данных на период рефакторинга репозитория
    //Заглушка на период подготовки BackEnd
    private val eventPlug: List<Event> = listOf<Event>(
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
                "pp123457.jpg"
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
                "00000038.jpg"
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
                "00000039.jpg"
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
                "00000040.jpg"
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
                "00000041.jpg"
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
                "000000043.jpg"
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
                "000000044.jpg"
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
                "000000045.jpg"
            )
        ),
    )

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
    }

    private val _prepareEventList = MutableLiveData<List<Event>>().apply {
        repository.getEventsListOfBook(30, "0000011", object : CallBack<List<Event>> {
            override fun onResult(result: List<Event>) {
                value = eventPlug
            }
        })
    }
    val prepareEventList: LiveData<List<Event>> = _prepareEventList
}