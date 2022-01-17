package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ActivityGraphVerticalSimpleItemBinding
import ru.vdv.myapp.myreadersdiary.domain.WeekEvent
import ru.vdv.myapp.myreadersdiary.ui.common.mark

class ActivityStatisticsGraphAdapter : RecyclerView.Adapter<ActivityStatisticsGraphViewHolder>() {
    var items: List<WeekEvent> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityStatisticsGraphViewHolder {
        val binding = ActivityGraphVerticalSimpleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ActivityStatisticsGraphViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityStatisticsGraphViewHolder, position: Int) {
        val item = items[position]
        holder.mark(item.weekMonday.numberOfContributions, holder.ivMonday)
        holder.mark(item.weekTuesday.numberOfContributions, holder.ivTuesday)
        holder.mark(item.weekWednesday.numberOfContributions, holder.ivWednesday)
        holder.mark(item.weekThursday.numberOfContributions, holder.ivThursday)
        holder.mark(item.weekFriday.numberOfContributions, holder.ivFriday)
        holder.mark(item.weekSaturday.numberOfContributions, holder.ivSaturday)
        holder.mark(item.weekSunday.numberOfContributions, holder.ivSunday)
        holder.tvBottomText.text = position.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}