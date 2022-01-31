package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListMonthSummaryStatisticGroupSeparatorBinding

class MonthSummaryStatisticGroupSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListMonthSummaryStatisticGroupSeparatorBinding = ItemListMonthSummaryStatisticGroupSeparatorBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    val title = binding.tvGroupTitle
    val count = binding.tvGroupCount
    val materialCardView = binding.mcvGroupSeparator
}