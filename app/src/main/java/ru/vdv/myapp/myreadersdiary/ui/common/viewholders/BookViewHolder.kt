package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListBookBinding

class BookViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    binding: ItemListBookBinding = ItemListBookBinding.inflate(li, parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    var bookName: TextView = binding.tvBookName
    var bookDescription: TextView = binding.tvBodyBookDescription
    var coverBook: ImageView = binding.ivBookCover
    var itemFrame = binding.itemFrame
    var cardView = binding.cardView
}