package ru.vdv.myapp.myreadersdiary.ui.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleBook: TextView = itemView.findViewById(R.id.textView_book_title)
}