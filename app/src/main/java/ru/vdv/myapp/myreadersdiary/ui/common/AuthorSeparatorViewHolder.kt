package ru.vdv.myapp.myreadersdiary.ui.common

import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListAuthorSeparatorBinding

class AuthorSeparatorViewHolder(binding: ItemListAuthorSeparatorBinding):RecyclerView.ViewHolder(binding.root) {
    var name = binding.authorSeparatorFullName
    var letter = binding.authorSeparatorLitter
}