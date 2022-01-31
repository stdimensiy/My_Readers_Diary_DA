package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListMonthSummaryStatisticBookSingleEventBinding

class MonthSummaryStatisticBookViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListMonthSummaryStatisticBookSingleEventBinding = ItemListMonthSummaryStatisticBookSingleEventBinding.inflate(
        li, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    val materialCardView = binding.mcvBookInProgress
    val bookCover = binding.ivBookCover
    val bookTitle = binding.tvEventObject
    val eventDescription = binding.tvEventDescription
}