package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListSummaryStatisticGraphBinding

class SummaryStatisticGraphViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    binding: ItemListSummaryStatisticGraphBinding = ItemListSummaryStatisticGraphBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    val materialCardView = binding.mcvSummaryStatisticGraph
    val eventGraph = binding.rvEventsGraph
    val iconDACoin = binding.ivDaCoinIcon
    val countDACoin = binding.tvActivityDaCoinCount
    val title = binding.tvActivityGraphTitle
}