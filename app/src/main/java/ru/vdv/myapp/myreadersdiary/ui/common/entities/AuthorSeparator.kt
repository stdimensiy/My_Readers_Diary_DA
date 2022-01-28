package ru.vdv.myapp.myreadersdiary.ui.common.entities

import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList

data class AuthorSeparator(
    val AuthorFullName: String
): ToBookList
