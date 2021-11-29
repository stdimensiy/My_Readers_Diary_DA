package ru.vdv.myapp.myreadersdiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Book

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    var items: List<Book> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return HomeViewHolder(root)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        holder.titleBook.text = item.title
    }

    override fun onViewDetachedFromWindow(holder: HomeViewHolder) {
        // не забыть занулить лисенеры
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}