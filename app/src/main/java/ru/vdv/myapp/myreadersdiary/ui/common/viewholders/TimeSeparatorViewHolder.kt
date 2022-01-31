package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListTimeSeparatorBinding

class TimeSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListTimeSeparatorBinding = ItemListTimeSeparatorBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    var title = binding.separatorTitle
}