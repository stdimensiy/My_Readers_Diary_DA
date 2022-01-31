package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListMonthSummaryStatisticFuterBinding

class MonthSummaryStatisticFuterViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListMonthSummaryStatisticFuterBinding = ItemListMonthSummaryStatisticFuterBinding.inflate(
        li, parent, false
    )): RecyclerView.ViewHolder(binding.root) {
}