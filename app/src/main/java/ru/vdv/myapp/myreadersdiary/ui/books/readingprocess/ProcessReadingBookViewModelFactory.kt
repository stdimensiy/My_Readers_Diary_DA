package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.vdv.myapp.myreadersdiary.domain.Book

/** Фабрика для создания ProcessReadingBookViewModel
 * в соответствии с документацией https://developer.android.com/codelabs/kotlin-android-training-view-model#7
 */
class ProcessReadingBookViewModelFactory(
    private val book: Book?,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (modelClass.isAssignableFrom(ProcessReadingBookViewModel::class.java)) {
            return ProcessReadingBookViewModel(
                book = book,
                state = handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
