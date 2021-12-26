package ru.vdv.myapp.myreadersdiary.model.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vdv.myapp.myreadersdiary.domain.*
import ru.vdv.myapp.myreadersdiary.model.api.ApiService
import ru.vdv.myapp.myreadersdiary.model.retrofit.Common
import java.util.*
import kotlin.random.Random.Default.nextInt

class RepositoryImpl() : Repository {

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
                    weekMonday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekTuesday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekWednesday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekThursday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekFriday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekSaturday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0),
                    weekSunday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, 0)

                )
            )
        }
        callBack.onResult(weekEvent)
    }

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
                    weekMonday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekTuesday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekWednesday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekThursday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekFriday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekSaturday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21)),
                    weekSunday = SummaryDayEvents(date = currentCalendarDate.apply { add(Calendar.DATE, 1) }.time, nextInt(0, 21))

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
                val dayOfWeakNormalized = (7 + dayOfWeak - 2) % 7 //Изначально get возвращает как 1 - воскресенье, 7 - суббота. Приводим к виду 0 - понедельник, 6 - воскресенье
                roll(Calendar.DATE, -dayOfWeakNormalized - 1)
            }
        }


    //Заглушка  недельных суммарных активностей, пока API готовится.
    @Deprecated("Using getClearSummaryEventData")
    private val weekEventPlug: List<WeekEvent> = listOf(
        WeekEvent(
            SummaryDayEvents(Date(2020, 11, 10), 0),
            SummaryDayEvents(Date(2020, 11, 11), 8),
            SummaryDayEvents(Date(2020, 11, 12), 0),
            SummaryDayEvents(Date(2020, 11, 13), 0),
            SummaryDayEvents(Date(2020, 11, 14), 0),
            SummaryDayEvents(Date(2020, 11, 15), 0),
            SummaryDayEvents(Date(2020, 11, 16), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 11, 17), 0),
            SummaryDayEvents(Date(2020, 11, 18), 0),
            SummaryDayEvents(Date(2020, 11, 19), 0),
            SummaryDayEvents(Date(2020, 11, 20), 0),
            SummaryDayEvents(Date(2020, 11, 21), 0),
            SummaryDayEvents(Date(2020, 11, 22), 10),
            SummaryDayEvents(Date(2020, 11, 23), 21),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 11, 24), 0),
            SummaryDayEvents(Date(2020, 11, 25), 0),
            SummaryDayEvents(Date(2020, 11, 26), 0),
            SummaryDayEvents(Date(2020, 11, 27), 0),
            SummaryDayEvents(Date(2020, 11, 28), 0),
            SummaryDayEvents(Date(2020, 11, 29), 0),
            SummaryDayEvents(Date(2020, 11, 30), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 12, 1), 0),
            SummaryDayEvents(Date(2020, 12, 2), 0),
            SummaryDayEvents(Date(2020, 12, 3), 2),
            SummaryDayEvents(Date(2020, 12, 4), 0),
            SummaryDayEvents(Date(2020, 12, 5), 0),
            SummaryDayEvents(Date(2020, 12, 6), 0),
            SummaryDayEvents(Date(2020, 12, 7), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 12, 8), 0),
            SummaryDayEvents(Date(2020, 12, 9), 0),
            SummaryDayEvents(Date(2020, 12, 10), 0),
            SummaryDayEvents(Date(2020, 12, 11), 0),
            SummaryDayEvents(Date(2020, 12, 12), 0),
            SummaryDayEvents(Date(2020, 12, 13), 0),
            SummaryDayEvents(Date(2020, 12, 14), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 12, 15), 4),
            SummaryDayEvents(Date(2020, 12, 16), 0),
            SummaryDayEvents(Date(2020, 12, 17), 0),
            SummaryDayEvents(Date(2020, 12, 18), 0),
            SummaryDayEvents(Date(2020, 12, 19), 12),
            SummaryDayEvents(Date(2020, 12, 20), 0),
            SummaryDayEvents(Date(2020, 12, 21), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 12, 22), 5),
            SummaryDayEvents(Date(2020, 12, 23), 8),
            SummaryDayEvents(Date(2020, 12, 24), 8),
            SummaryDayEvents(Date(2020, 12, 25), 0),
            SummaryDayEvents(Date(2020, 12, 26), 10),
            SummaryDayEvents(Date(2020, 12, 27), 10),
            SummaryDayEvents(Date(2020, 12, 28), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2020, 12, 29), 2),
            SummaryDayEvents(Date(2020, 12, 30), 0),
            SummaryDayEvents(Date(2020, 12, 31), 0),
            SummaryDayEvents(Date(2021, 1, 1), 0),
            SummaryDayEvents(Date(2021, 1, 2), 0),
            SummaryDayEvents(Date(2021, 1, 3), 0),
            SummaryDayEvents(Date(2021, 1, 4), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 1, 5), 0),
            SummaryDayEvents(Date(2021, 1, 6), 18),
            SummaryDayEvents(Date(2021, 1, 7), 10),
            SummaryDayEvents(Date(2021, 1, 8), 0),
            SummaryDayEvents(Date(2021, 1, 9), 22),
            SummaryDayEvents(Date(2021, 1, 10), 8),
            SummaryDayEvents(Date(2021, 1, 11), 6),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 1, 12), 12),
            SummaryDayEvents(Date(2021, 1, 13), 0),
            SummaryDayEvents(Date(2021, 1, 14), 6),
            SummaryDayEvents(Date(2021, 1, 15), 17),
            SummaryDayEvents(Date(2021, 1, 16), 5),
            SummaryDayEvents(Date(2021, 1, 17), 0),
            SummaryDayEvents(Date(2021, 1, 18), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 1, 19), 5),
            SummaryDayEvents(Date(2021, 1, 20), 8),
            SummaryDayEvents(Date(2021, 1, 21), 8),
            SummaryDayEvents(Date(2021, 1, 22), 0),
            SummaryDayEvents(Date(2021, 1, 23), 10),
            SummaryDayEvents(Date(2021, 1, 24), 10),
            SummaryDayEvents(Date(2021, 1, 25), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 1, 29), 5),
            SummaryDayEvents(Date(2021, 1, 30), 10),
            SummaryDayEvents(Date(2021, 1, 31), 10),
            SummaryDayEvents(Date(2021, 2, 1), 12),
            SummaryDayEvents(Date(2021, 2, 2), 6),
            SummaryDayEvents(Date(2021, 2, 3), 6),
            SummaryDayEvents(Date(2021, 2, 4), 8),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 2, 5), 5),
            SummaryDayEvents(Date(2021, 2, 6), 8),
            SummaryDayEvents(Date(2021, 2, 7), 8),
            SummaryDayEvents(Date(2021, 2, 8), 0),
            SummaryDayEvents(Date(2021, 2, 9), 10),
            SummaryDayEvents(Date(2021, 2, 10), 10),
            SummaryDayEvents(Date(2021, 2, 11), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 2, 12), 10),
            SummaryDayEvents(Date(2021, 2, 13), 0),
            SummaryDayEvents(Date(2021, 2, 14), 0),
            SummaryDayEvents(Date(2021, 2, 15), 2),
            SummaryDayEvents(Date(2021, 2, 16), 10),
            SummaryDayEvents(Date(2021, 2, 17), 4),
            SummaryDayEvents(Date(2021, 2, 18), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 2, 19), 5),
            SummaryDayEvents(Date(2021, 2, 20), 8),
            SummaryDayEvents(Date(2021, 2, 21), 8),
            SummaryDayEvents(Date(2021, 2, 22), 0),
            SummaryDayEvents(Date(2021, 2, 23), 10),
            SummaryDayEvents(Date(2021, 2, 24), 10),
            SummaryDayEvents(Date(2021, 2, 25), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 2, 26), 8),
            SummaryDayEvents(Date(2021, 2, 27), 8),
            SummaryDayEvents(Date(2021, 2, 28), 4),
            SummaryDayEvents(Date(2021, 3, 1), 0),
            SummaryDayEvents(Date(2021, 3, 2), 10),
            SummaryDayEvents(Date(2021, 3, 3), 4),
            SummaryDayEvents(Date(2021, 3, 4), 6),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 3, 5), 6),
            SummaryDayEvents(Date(2021, 3, 6), 12),
            SummaryDayEvents(Date(2021, 3, 7), 0),
            SummaryDayEvents(Date(2021, 3, 8), 0),
            SummaryDayEvents(Date(2021, 3, 9), 0),
            SummaryDayEvents(Date(2021, 3, 10), 0),
            SummaryDayEvents(Date(2021, 3, 11), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 3, 12), 10),
            SummaryDayEvents(Date(2021, 3, 13), 5),
            SummaryDayEvents(Date(2021, 3, 14), 0),
            SummaryDayEvents(Date(2021, 3, 15), 2),
            SummaryDayEvents(Date(2021, 3, 16), 14),
            SummaryDayEvents(Date(2021, 3, 17), 0),
            SummaryDayEvents(Date(2021, 3, 18), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 3, 19), 8),
            SummaryDayEvents(Date(2021, 3, 20), 10),
            SummaryDayEvents(Date(2021, 3, 21), 21),
            SummaryDayEvents(Date(2021, 3, 22), 14),
            SummaryDayEvents(Date(2021, 3, 23), 12),
            SummaryDayEvents(Date(2021, 3, 24), 2),
            SummaryDayEvents(Date(2021, 3, 25), 6),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 3, 26), 3),
            SummaryDayEvents(Date(2021, 3, 27), 12),
            SummaryDayEvents(Date(2021, 3, 28), 2),
            SummaryDayEvents(Date(2021, 3, 29), 8),
            SummaryDayEvents(Date(2021, 3, 30), 6),
            SummaryDayEvents(Date(2021, 3, 31), 0),
            SummaryDayEvents(Date(2021, 4, 1), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 4, 2), 0),
            SummaryDayEvents(Date(2021, 4, 3), 0),
            SummaryDayEvents(Date(2021, 4, 4), 0),
            SummaryDayEvents(Date(2021, 4, 5), 0),
            SummaryDayEvents(Date(2021, 4, 6), 8),
            SummaryDayEvents(Date(2021, 4, 7), 4),
            SummaryDayEvents(Date(2021, 4, 8), 10),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 4, 4), 0),
            SummaryDayEvents(Date(2021, 4, 5), 5),
            SummaryDayEvents(Date(2021, 4, 6), 0),
            SummaryDayEvents(Date(2021, 4, 7), 2),
            SummaryDayEvents(Date(2021, 4, 8), 0),
            SummaryDayEvents(Date(2021, 4, 9), 0),
            SummaryDayEvents(Date(2021, 4, 10), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 4, 11), 10),
            SummaryDayEvents(Date(2021, 4, 12), 5),
            SummaryDayEvents(Date(2021, 4, 13), 0),
            SummaryDayEvents(Date(2021, 4, 14), 2),
            SummaryDayEvents(Date(2021, 4, 15), 14),
            SummaryDayEvents(Date(2021, 4, 16), 0),
            SummaryDayEvents(Date(2021, 4, 17), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 4, 18), 16),
            SummaryDayEvents(Date(2021, 4, 19), 10),
            SummaryDayEvents(Date(2021, 4, 20), 8),
            SummaryDayEvents(Date(2021, 4, 21), 6),
            SummaryDayEvents(Date(2021, 4, 22), 4),
            SummaryDayEvents(Date(2021, 4, 23), 2),
            SummaryDayEvents(Date(2021, 4, 24), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 4, 25), 4),
            SummaryDayEvents(Date(2021, 4, 26), 5),
            SummaryDayEvents(Date(2021, 4, 27), 8),
            SummaryDayEvents(Date(2021, 4, 28), 6),
            SummaryDayEvents(Date(2021, 4, 29), 2),
            SummaryDayEvents(Date(2021, 4, 30), 10),
            SummaryDayEvents(Date(2021, 5, 1), 1),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 5, 2), 10),
            SummaryDayEvents(Date(2021, 5, 3), 5),
            SummaryDayEvents(Date(2021, 5, 4), 0),
            SummaryDayEvents(Date(2021, 5, 5), 2),
            SummaryDayEvents(Date(2021, 5, 6), 14),
            SummaryDayEvents(Date(2021, 5, 7), 0),
            SummaryDayEvents(Date(2021, 5, 8), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 5, 9), 2),
            SummaryDayEvents(Date(2021, 5, 10), 6),
            SummaryDayEvents(Date(2021, 5, 11), 0),
            SummaryDayEvents(Date(2021, 5, 12), 8),
            SummaryDayEvents(Date(2021, 5, 13), 0),
            SummaryDayEvents(Date(2021, 5, 14), 10),
            SummaryDayEvents(Date(2021, 5, 15), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 5, 16), 0),
            SummaryDayEvents(Date(2021, 5, 17), 10),
            SummaryDayEvents(Date(2021, 5, 18), 2),
            SummaryDayEvents(Date(2021, 5, 19), 4),
            SummaryDayEvents(Date(2021, 5, 20), 6),
            SummaryDayEvents(Date(2021, 5, 21), 10),
            SummaryDayEvents(Date(2021, 5, 22), 8),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 5, 23), 9),
            SummaryDayEvents(Date(2021, 5, 24), 4),
            SummaryDayEvents(Date(2021, 5, 25), 6),
            SummaryDayEvents(Date(2021, 5, 26), 8),
            SummaryDayEvents(Date(2021, 5, 27), 10),
            SummaryDayEvents(Date(2021, 5, 28), 4),
            SummaryDayEvents(Date(2021, 5, 29), 10),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 5, 30), 6),
            SummaryDayEvents(Date(2021, 5, 31), 14),
            SummaryDayEvents(Date(2021, 6, 1), 2),
            SummaryDayEvents(Date(2021, 6, 2), 12),
            SummaryDayEvents(Date(2021, 6, 3), 0),
            SummaryDayEvents(Date(2021, 6, 4), 0),
            SummaryDayEvents(Date(2021, 6, 5), 3),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 6, 6), 6),
            SummaryDayEvents(Date(2021, 6, 7), 5),
            SummaryDayEvents(Date(2021, 6, 8), 0),
            SummaryDayEvents(Date(2021, 6, 9), 2),
            SummaryDayEvents(Date(2021, 6, 10), 6),
            SummaryDayEvents(Date(2021, 6, 11), 0),
            SummaryDayEvents(Date(2021, 6, 12), 10),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 6, 13), 6),
            SummaryDayEvents(Date(2021, 6, 14), 0),
            SummaryDayEvents(Date(2021, 6, 15), 10),
            SummaryDayEvents(Date(2021, 6, 16), 6),
            SummaryDayEvents(Date(2021, 6, 17), 0),
            SummaryDayEvents(Date(2021, 6, 18), 12),
            SummaryDayEvents(Date(2021, 6, 19), 20),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 6, 20), 6),
            SummaryDayEvents(Date(2021, 6, 21), 5),
            SummaryDayEvents(Date(2021, 6, 22), 4),
            SummaryDayEvents(Date(2021, 6, 23), 12),
            SummaryDayEvents(Date(2021, 6, 24), 10),
            SummaryDayEvents(Date(2021, 6, 25), 8),
            SummaryDayEvents(Date(2021, 6, 26), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 6, 27), 0),
            SummaryDayEvents(Date(2021, 6, 28), 6),
            SummaryDayEvents(Date(2021, 6, 29), 2),
            SummaryDayEvents(Date(2021, 6, 30), 14),
            SummaryDayEvents(Date(2021, 7, 1), 7),
            SummaryDayEvents(Date(2021, 7, 2), 6),
            SummaryDayEvents(Date(2021, 7, 3), 0),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 7, 4), 0),
            SummaryDayEvents(Date(2021, 7, 5), 0),
            SummaryDayEvents(Date(2021, 7, 6), 0),
            SummaryDayEvents(Date(2021, 7, 7), 4),
            SummaryDayEvents(Date(2021, 7, 8), 12),
            SummaryDayEvents(Date(2021, 7, 9), 6),
            SummaryDayEvents(Date(2021, 7, 10), 24),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 7, 11), 2),
            SummaryDayEvents(Date(2021, 7, 12), 10),
            SummaryDayEvents(Date(2021, 7, 13), 2),
            SummaryDayEvents(Date(2021, 7, 14), 14),
            SummaryDayEvents(Date(2021, 7, 15), 0),
            SummaryDayEvents(Date(2021, 7, 16), 8),
            SummaryDayEvents(Date(2021, 7, 17), 2),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 7, 18), 2),
            SummaryDayEvents(Date(2021, 7, 19), 14),
            SummaryDayEvents(Date(2021, 7, 20), 6),
            SummaryDayEvents(Date(2021, 7, 21), 11),
            SummaryDayEvents(Date(2021, 7, 22), 0),
            SummaryDayEvents(Date(2021, 7, 23), 14),
            SummaryDayEvents(Date(2021, 7, 24), 0),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 7, 25), 25),
            SummaryDayEvents(Date(2021, 7, 26), 21),
            SummaryDayEvents(Date(2021, 7, 27), 10),
            SummaryDayEvents(Date(2021, 7, 28), 14),
            SummaryDayEvents(Date(2021, 7, 29), 20),
            SummaryDayEvents(Date(2021, 7, 30), 0),
            SummaryDayEvents(Date(2021, 8, 1), 0),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 8, 2), 0),
            SummaryDayEvents(Date(2021, 8, 3), 1),
            SummaryDayEvents(Date(2021, 8, 4), 2),
            SummaryDayEvents(Date(2021, 8, 5), 3),
            SummaryDayEvents(Date(2021, 8, 6), 4),
            SummaryDayEvents(Date(2021, 8, 7), 5),
            SummaryDayEvents(Date(2021, 8, 8), 6),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 8, 9), 10),
            SummaryDayEvents(Date(2021, 8, 10), 5),
            SummaryDayEvents(Date(2021, 8, 11), 0),
            SummaryDayEvents(Date(2021, 8, 12), 0),
            SummaryDayEvents(Date(2021, 8, 13), 0),
            SummaryDayEvents(Date(2021, 8, 14), 0),
            SummaryDayEvents(Date(2021, 8, 15), 0),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 8, 16), 2),
            SummaryDayEvents(Date(2021, 8, 17), 5),
            SummaryDayEvents(Date(2021, 8, 18), 0),
            SummaryDayEvents(Date(2021, 8, 19), 2),
            SummaryDayEvents(Date(2021, 8, 20), 1),
            SummaryDayEvents(Date(2021, 8, 21), 0),
            SummaryDayEvents(Date(2021, 8, 22), 12),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 8, 23), 6),
            SummaryDayEvents(Date(2021, 8, 24), 8),
            SummaryDayEvents(Date(2021, 8, 25), 14),
            SummaryDayEvents(Date(2021, 8, 26), 12),
            SummaryDayEvents(Date(2021, 8, 27), 9),
            SummaryDayEvents(Date(2021, 8, 28), 6),
            SummaryDayEvents(Date(2021, 8, 29), 6),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 8, 30), 4),
            SummaryDayEvents(Date(2021, 8, 31), 12),
            SummaryDayEvents(Date(2021, 9, 1), 3),
            SummaryDayEvents(Date(2021, 9, 2), 7),
            SummaryDayEvents(Date(2021, 9, 3), 0),
            SummaryDayEvents(Date(2021, 9, 4), 14),
            SummaryDayEvents(Date(2021, 9, 5), 1),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 9, 6), 0),
            SummaryDayEvents(Date(2021, 9, 7), 8),
            SummaryDayEvents(Date(2021, 9, 8), 0),
            SummaryDayEvents(Date(2021, 9, 9), 6),
            SummaryDayEvents(Date(2021, 9, 10), 10),
            SummaryDayEvents(Date(2021, 9, 11), 2),
            SummaryDayEvents(Date(2021, 9, 12), 0),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 9, 13), 4),
            SummaryDayEvents(Date(2021, 9, 14), 8),
            SummaryDayEvents(Date(2021, 9, 15), 2),
            SummaryDayEvents(Date(2021, 9, 16), 0),
            SummaryDayEvents(Date(2021, 9, 17), 10),
            SummaryDayEvents(Date(2021, 9, 18), 6),
            SummaryDayEvents(Date(2021, 9, 19), 4),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 9, 20), 10),
            SummaryDayEvents(Date(2021, 9, 21), 5),
            SummaryDayEvents(Date(2021, 9, 22), 0),
            SummaryDayEvents(Date(2021, 9, 23), 2),
            SummaryDayEvents(Date(2021, 9, 24), 14),
            SummaryDayEvents(Date(2021, 9, 25), 0),
            SummaryDayEvents(Date(2021, 9, 26), 12),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 9, 27), 8),
            SummaryDayEvents(Date(2021, 9, 28), 0),
            SummaryDayEvents(Date(2021, 9, 29), 6),
            SummaryDayEvents(Date(2021, 9, 30), 12),
            SummaryDayEvents(Date(2021, 10, 1), 8),
            SummaryDayEvents(Date(2021, 10, 2), 1),
            SummaryDayEvents(Date(2021, 10, 3), 4),
        ),
        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 4), 3),
            SummaryDayEvents(Date(2021, 10, 5), 6),
            SummaryDayEvents(Date(2021, 10, 6), 12),
            SummaryDayEvents(Date(2021, 10, 7), 0),
            SummaryDayEvents(Date(2021, 10, 8), 14),
            SummaryDayEvents(Date(2021, 10, 9), 2),
            SummaryDayEvents(Date(2021, 10, 10), 8),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 11), 10),
            SummaryDayEvents(Date(2021, 10, 12), 5),
            SummaryDayEvents(Date(2021, 10, 13), 8),
            SummaryDayEvents(Date(2021, 10, 14), 2),
            SummaryDayEvents(Date(2021, 10, 15), 10),
            SummaryDayEvents(Date(2021, 10, 16), 1),
            SummaryDayEvents(Date(2021, 10, 17), 8),
        ),

        WeekEvent(
            SummaryDayEvents(Date(2021, 10, 18), 0),
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