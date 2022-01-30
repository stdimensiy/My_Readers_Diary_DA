package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList

data class EventWithProgress(
    val baseObject: Book,
    val eventDescription: String = "",
    val progressPercentPrimary: Int = 0,
    val progressPercentSecondary: Int = 0,
    val eventFinalCount: String = ""

) : ToStatList
