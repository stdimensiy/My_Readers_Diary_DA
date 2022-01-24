package ru.vdv.myapp.myreadersdiary.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ru.vdv.myapp.myreadersdiary.databinding.EventListItemBinding
import ru.vdv.myapp.myreadersdiary.databinding.NewEventListItemBinding

class MainEventViewHolder(binding: NewEventListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    //var titleEvent: TextView = binding.textViewEventTitle
    //var fixDataEvent: TextView = binding.textViewFixDataEvent
    //var eventBody: TextView = binding.textViewEventBody
    var bookName: TextView = binding.subjectTextView
    var coverBook: ImageView = binding.imageViewBookCover
//    var signOfEventAddBook: ImageView = binding.imageViewEventAddBook
}