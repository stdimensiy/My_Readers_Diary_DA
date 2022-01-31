package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListMonthSummaryStatisticBookInProgressBinding

class MonthSummaryStatisticEventViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListMonthSummaryStatisticBookInProgressBinding = ItemListMonthSummaryStatisticBookInProgressBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    val materialCardView = binding.mcvBookInProgress
    val bookCover = binding.ivBookCover
    val processCount = binding.tvEventObjectCurrentResult
    val process = binding.pbEventObjectCurrentResult
    val bookTitle = binding.tvEventObject
    val processTitle = binding.tvEventObjectCurrentResultTitle
}