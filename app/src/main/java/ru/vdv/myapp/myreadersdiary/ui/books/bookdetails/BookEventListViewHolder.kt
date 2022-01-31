package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListEventOfBookBinding

class BookEventListViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListEventOfBookBinding = ItemListEventOfBookBinding.inflate(
        li,
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {
    var titleEvent: TextView = binding.tvEventTitle
    var fixDataEvent: TextView = binding.tvFixDataEvent
}