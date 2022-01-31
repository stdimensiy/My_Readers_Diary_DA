package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToMainList

data class TimeSeparator(
    val title: String
): ToMainList, ToBookList
