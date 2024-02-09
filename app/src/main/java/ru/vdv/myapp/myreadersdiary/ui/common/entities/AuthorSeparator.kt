package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList

data class AuthorSeparator(
    val authorFullName: String
) : ToBookList
