package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.WeekEvent

class ActivityStatisticsGraphAdapter : RecyclerView.Adapter<ActivityStatisticsGraphViewHolder>() {
    var items: List<WeekEvent> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityStatisticsGraphViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_graph_vertical_simple_item, parent, false)
        return ActivityStatisticsGraphViewHolder(root)
    }

    override fun onBindViewHolder(holder: ActivityStatisticsGraphViewHolder, position: Int) {
        val item = items[position]
        mark(item.weekMonday.numberOfContributions, holder.ivMonday)
        mark(item.weekTuesday.numberOfContributions, holder.ivTuesday)
        mark(item.weekWednesday.numberOfContributions, holder.ivWednesday)
        mark(item.weekThursday.numberOfContributions, holder.ivThursday)
        mark(item.weekFriday.numberOfContributions, holder.ivFriday)
        mark(item.weekSaturday.numberOfContributions, holder.ivSaturday)
        mark(item.weekSunday.numberOfContributions, holder.ivSunday)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun mark(count: Int, view: ImageView) {
        when (count) {
            0 -> view.setImageResource(R.drawable.round_square_indigo_50_24dp)
            in 1..2 -> view.setImageResource(R.drawable.round_square_indigo_100_24dp)
            in 3..4 -> view.setImageResource(R.drawable.round_square_indigo_200_24dp)
            in 5..6 -> view.setImageResource(R.drawable.round_square_indigo_300_24dp)
            in 7..8 -> view.setImageResource(R.drawable.round_square_indigo_400_24dp)
            in 9..10 -> view.setImageResource(R.drawable.round_square_indigo_500_24dp)
            in 11..12 -> view.setImageResource(R.drawable.round_square_indigo_600_24dp)
            in 13..14 -> view.setImageResource(R.drawable.round_square_indigo_700_24dp)
            in 15..16 -> view.setImageResource(R.drawable.round_square_indigo_800_24dp)
            in 17..10000 -> view.setImageResource(R.drawable.round_square_indigo_900_24dp)
        }

    }
}