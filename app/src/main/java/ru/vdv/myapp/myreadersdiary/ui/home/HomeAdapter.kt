package ru.vdv.myapp.myreadersdiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.IImageLoader

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    var items: List<Book> = listOf()
    val imageLoader: IImageLoader<ImageView> = GlideImageLoader()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return HomeViewHolder(root)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        holder.titleBook.text = item.title
        ("${item.producerName} ${item.producerPatronymic} ${item.producerSurname}").also {
            holder.authorBook.text = it
        }
        imageLoader.loadBookCover("https://dadapproves.ru/usercontent/book/covers/${item.bookCover}" , holder.coverBook)
    }

    override fun onViewDetachedFromWindow(holder: HomeViewHolder) {
        // не забыть занулить лисенеры
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}