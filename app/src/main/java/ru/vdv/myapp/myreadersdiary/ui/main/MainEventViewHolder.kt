package ru.vdv.myapp.myreadersdiary.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.EventListItemBinding

class MainEventViewHolder(binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    var titleEvent: TextView = binding.textViewEventTitle
    var fixDataEvent: TextView = binding.textViewFixDataEvent
    var eventBody: TextView = binding.textViewEventBody
    var coverBook: ImageView = binding.imageViewBookCover
    var signOfEventAddBook: ImageView = binding.imageViewEventAddBook
}