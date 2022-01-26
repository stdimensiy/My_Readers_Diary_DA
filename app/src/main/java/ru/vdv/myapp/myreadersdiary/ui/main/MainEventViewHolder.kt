package ru.vdv.myapp.myreadersdiary.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.NewEventListItemBinding

class MainEventViewHolder(binding: NewEventListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var bookName: TextView = binding.tvBookName
    var bookDescription: TextView = binding.tvBodyBookDescription
    var coverBook: ImageView = binding.ivBookCover
}