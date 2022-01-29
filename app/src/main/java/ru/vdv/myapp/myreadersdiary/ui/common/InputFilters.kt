package ru.vdv.myapp.myreadersdiary.ui.common

import android.text.InputFilter

/**
 * Реализация фильтров ввода
 */
object InputFilters {
    private val DIGITS: CharArray = "0123456789".toCharArray()

    /** Фильтр ввода цифр */
    val digitsFilter = InputFilter { source, start, end, _, _, _ ->
        val (filteredStringBuilder, shouldKeepOriginal) = filterDigitsLine(source, start, end)
        when {
            shouldKeepOriginal -> null
            else -> filteredStringBuilder.toString()
        }
    }

    private fun filterDigitsLine(line: CharSequence, start: Int, end: Int): Pair<StringBuilder, Boolean> {
        val filteredStringBuilder = StringBuilder()
        var shouldKeepOriginal = true
        for (i in start until end) {
            val currentChar = line[i]
            if (checkDigitsIsValid(currentChar)) {
                filteredStringBuilder.append(currentChar)
            } else {
                shouldKeepOriginal = false
            }
        }
        return filteredStringBuilder to shouldKeepOriginal
    }

    private fun checkDigitsIsValid(char: Char): Boolean {
        return char in DIGITS
    }
}
