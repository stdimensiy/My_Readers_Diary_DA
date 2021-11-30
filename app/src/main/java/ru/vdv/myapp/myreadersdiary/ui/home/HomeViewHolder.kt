package ru.vdv.myapp.myreadersdiary.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleBook: TextView = itemView.findViewById(R.id.textView_book_title)
    var authorBook: TextView = itemView.findViewById(R.id.textView_authors_of_the_book)
    var status: TextView = itemView.findViewById(R.id.textView_book_status)
    var coverBook: ImageView = itemView.findViewById(R.id.imageView_book_cover)
    var signOfApproval: ImageView = itemView.findViewById(R.id.imageView_sign_of_approval)
}