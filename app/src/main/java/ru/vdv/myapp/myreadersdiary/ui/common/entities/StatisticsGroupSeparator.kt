package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList

data class StatisticsGroupSeparator(
    val groupTitle: String,
    val groupSummaryValue: Int
) : ToStatList
