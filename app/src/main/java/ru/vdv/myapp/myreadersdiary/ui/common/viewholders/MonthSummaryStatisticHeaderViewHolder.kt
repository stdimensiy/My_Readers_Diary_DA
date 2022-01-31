package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListMonthSummaryStatisticHeaderBinding

class MonthSummaryStatisticHeaderViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListMonthSummaryStatisticHeaderBinding = ItemListMonthSummaryStatisticHeaderBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    val iconDACoin = binding.ivDACoinIcon
    val materialCardView = binding.mcvGroupSeparator
    val titleDACoin = binding.tvCountDACoinTitle
    val countDACoin = binding.tvDACoinCount
    val title = binding.tvHeaderTitle
    val period = binding.tvPeriod
}