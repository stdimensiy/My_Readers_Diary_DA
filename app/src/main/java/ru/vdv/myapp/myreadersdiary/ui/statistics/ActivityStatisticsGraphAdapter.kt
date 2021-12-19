package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.tvBottomText.text = position.toString()
        
    }

    override fun getItemCount(): Int {
        return items.size
    }
}