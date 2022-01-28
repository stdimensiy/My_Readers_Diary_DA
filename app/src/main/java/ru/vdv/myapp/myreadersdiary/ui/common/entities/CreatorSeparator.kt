package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList

data class CreatorSeparator(
    val user: User
): ToBookList