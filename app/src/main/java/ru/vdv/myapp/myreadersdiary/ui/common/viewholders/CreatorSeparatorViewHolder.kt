package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListCreatorSeparatorBinding

class CreatorSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListCreatorSeparatorBinding = ItemListCreatorSeparatorBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    var creator = binding.creatorSeparatorTitle
}