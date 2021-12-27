package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ActivityGraphVerticalSimpleItemBinding

class ActivityStatisticsGraphViewHolder(val binding: ActivityGraphVerticalSimpleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var ivMonday: ImageView = binding.ivMon
    var ivTuesday: ImageView = binding.ivTue
    var ivWednesday: ImageView = binding.ivWed
    var ivThursday: ImageView = binding.ivThu
    var ivFriday: ImageView = binding.ivFri
    var ivSaturday: ImageView = binding.ivSat
    var ivSunday: ImageView = binding.ivSun
    var tvBottomText: TextView = binding.tvWeekBottomText
}