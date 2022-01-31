package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList

data class TotalMonthlyActivityHeader(
    val title: String,
    val content: String,
    val numValue: Int
) : ToStatList
