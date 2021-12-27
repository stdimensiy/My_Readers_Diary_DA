package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.ShortEventForBook

class BookEventListAdapter : RecyclerView.Adapter<BookEventListViewHolder>() {
    var items: List<ShortEventForBook> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookEventListViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_of_book_list_item, parent, false)
        return BookEventListViewHolder(root)
    }

    override fun onBindViewHolder(holder: BookEventListViewHolder, position: Int) {
        val item = items[position]
        holder.titleEvent.text = item.title
        holder.fixDataEvent.text = item.representedDateTime.toString()
    }

    override fun onViewDetachedFromWindow(holder: BookEventListViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}