package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList

data class SingleEvent(
    val baseObject: Book,
    val eventDescription: String = "",
): ToStatList
