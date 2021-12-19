package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R

class BookEventListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleEvent: TextView = itemView.findViewById(R.id.textView_event_title)
    var fixDataEvent: TextView = itemView.findViewById(R.id.textView_fix_data_event)
    var eventBody: TextView = itemView.findViewById(R.id.textView_event_body)
    var coverBook: ImageView = itemView.findViewById(R.id.imageView_book_cover)
    var signOfEventAddBook: ImageView = itemView.findViewById(R.id.imageView_event_add_book)
}