package ru.vdv.myapp.myreadersdiary.ui.common

import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListGroupSeparatorBinding

class GroupSeparatorViewHolder(binding: ItemListGroupSeparatorBinding):RecyclerView.ViewHolder(binding.root) {
    var groupName = binding.groupSeparatorTitle
}