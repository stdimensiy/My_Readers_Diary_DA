package ru.vdv.myapp.myreadersdiary.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants

class ListOfBooksAdapter : RecyclerView.Adapter<ListOfBooksViewHolder>() {
    var items: List<Book> = listOf()
    private val imageLoader: ImageLoader<ImageView> = GlideImageLoader()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfBooksViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return ListOfBooksViewHolder(root)
    }

    override fun onBindViewHolder(holderOfBooks: ListOfBooksViewHolder, position: Int) {
        val item = items[position]
        holderOfBooks.titleBook.text = item.title
        ("${item.producerName} ${item.producerPatronymic} ${item.producerSurname}").also {
            holderOfBooks.authorBook.text = it
        }
        imageLoader.loadBookCover(item.bookCover, holderOfBooks.coverBook)
    }

    override fun onViewAttachedToWindow(holderOfBooks: ListOfBooksViewHolder) {
        val item = items[holderOfBooks.adapterPosition]
        holderOfBooks.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, item)
            holderOfBooks.itemView.findNavController()
                .navigate(R.id.nav_book_details_fragment, bundle)
        }
        super.onViewAttachedToWindow(holderOfBooks)
    }

    override fun onViewDetachedFromWindow(holderOfBooks: ListOfBooksViewHolder) {
        holderOfBooks.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holderOfBooks)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}