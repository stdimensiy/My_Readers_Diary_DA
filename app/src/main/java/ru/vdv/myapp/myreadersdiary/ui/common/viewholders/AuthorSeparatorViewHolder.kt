package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListAuthorSeparatorBinding

class AuthorSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListAuthorSeparatorBinding = ItemListAuthorSeparatorBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    var name = binding.authorSeparatorFullName
    var letter = binding.authorSeparatorLetter
}