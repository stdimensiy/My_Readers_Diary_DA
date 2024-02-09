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
import ru.vdv.myapp.myreadersdiary.ui.common.entities.AuthorSeparator
import ru.vdv.myapp.myreadersdiary.ui.common.entities.CreatorSeparator
import ru.vdv.myapp.myreadersdiary.ui.common.entities.GroupSeparator
import ru.vdv.myapp.myreadersdiary.ui.common.entities.TimeSeparator
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToBookList
import ru.vdv.myapp.myreadersdiary.ui.common.viewholders.*

class ListOfBooksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<ToBookList> = listOf()
    private val imageLoader: ImageLoader<ImageView> = GlideImageLoader()

    override fun getItemViewType(position: Int) = when (items[position]) {
        is Book -> BaseViewType.USUAL
        is AuthorSeparator -> BaseViewType.AUTHOR_SEPARATOR
        is CreatorSeparator -> BaseViewType.CREATOR_SEPARATOR
        is GroupSeparator -> BaseViewType.GROUP_SEPARATOR
        else -> BaseViewType.UNKNOWN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BaseViewType.USUAL -> BookViewHolder(layoutInflater, parent)
            BaseViewType.TIME_SEPARATOR -> TimeSeparatorViewHolder(layoutInflater, parent)
            BaseViewType.AUTHOR_SEPARATOR -> AuthorSeparatorViewHolder(layoutInflater, parent)
            BaseViewType.GROUP_SEPARATOR -> GroupSeparatorViewHolder(layoutInflater, parent)
            else -> UnknownTypeViewHolder(layoutInflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]
        when (holder) {
            is BookViewHolder -> {
                item as Book
                holder.bookName.text = item.title
                (item.producerName + " ${item.producerPatronymic}" +
                        " ${item.producerSurname}"
                        + " / " + " ${item.title}").also {
                    holder.bookDescription.text = it
                }
                imageLoader.loadBookCover(item.bookCover, holder.coverBook)
            }
            is TimeSeparatorViewHolder -> {
                item as TimeSeparator
                holder.title.text = item.title
            }
            is AuthorSeparatorViewHolder -> {
                item as AuthorSeparator
                holder.letter.text = item.authorFullName[0].toString()
                holder.name.text = item.authorFullName
            }
            is GroupSeparatorViewHolder -> {
                item as GroupSeparator
                holder.groupName.text = item.groupName
            }
            else -> {
                holder as UnknownTypeViewHolder
                holder.title.text = "ID: $position, type: ${item.javaClass.simpleName}"
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is BookViewHolder -> {
                holder.cardView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable(
                        BaseConstants.MY_BOOK_BUNDLE_KEY,
                        items[holder.adapterPosition] as Book
                    )
                    holder.itemView.findNavController()
                        .navigate(R.id.nav_book_details_fragment, bundle)
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holderOfBooks: RecyclerView.ViewHolder) {
        holderOfBooks.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holderOfBooks)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}