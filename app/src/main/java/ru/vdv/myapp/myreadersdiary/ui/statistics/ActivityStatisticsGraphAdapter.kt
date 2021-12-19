package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.domain.WeekEvent

class ActivityStatisticsGraphAdapter: RecyclerView.Adapter<ActivityStatisticsGraphViewHolder>() {
    var items: List<WeekEvent> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityStatisticsGraphViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ActivityStatisticsGraphViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return items.size
    }
}