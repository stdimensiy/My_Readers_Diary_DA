package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListGroupSeparatorBinding

class GroupSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListGroupSeparatorBinding = ItemListGroupSeparatorBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    var groupName = binding.groupSeparatorTitle
}