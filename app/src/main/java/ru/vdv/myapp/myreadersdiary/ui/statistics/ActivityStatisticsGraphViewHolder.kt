package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R

class ActivityStatisticsGraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ivMonday: ImageView = itemView.findViewById(R.id.iv_mon)
    var ivTuesday: ImageView = itemView.findViewById(R.id.iv_tue)
    var ivWednesday: ImageView = itemView.findViewById(R.id.iv_wed)
    var ivThursday: ImageView = itemView.findViewById(R.id.iv_thu)
    var ivFriday: ImageView = itemView.findViewById(R.id.iv_fri)
    var ivSaturday: ImageView = itemView.findViewById(R.id.iv_sat)
    var ivSunday: ImageView = itemView.findViewById(R.id.iv_sun)
    var tvBottomText: TextView = itemView.findViewById(R.id.tv_week_bottom_text)
}