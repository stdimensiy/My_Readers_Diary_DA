package ru.vdv.myapp.myreadersdiary.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.ItemListTimeSeparatorBinding
import ru.vdv.myapp.myreadersdiary.databinding.NewEventListItemBinding
import ru.vdv.myapp.myreadersdiary.databinding.UnknownTypeListItemBinding
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.TimeSeparator
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.*

class MainEventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<ToMainList> = listOf()
    val imageLoader: ImageLoader<ImageView> = GlideImageLoader()

    override fun getItemViewType(position: Int) = when (items[position]) {
        is Event -> BaseViewType.USUAL
        is TimeSeparator -> BaseViewType.TIME_SEPARATOR
        else -> BaseViewType.UNKNOWN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            BaseViewType.TIME_SEPARATOR -> {
                val binding =
                    ItemListTimeSeparatorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return TimeSeparatorViewHolder(binding)
            }
            BaseViewType.USUAL -> {
                val binding =
                    NewEventListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MainEventViewHolder(binding)
            }
            else -> {
                val binding =
                    UnknownTypeListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return UnknownTypeViewHolder(binding)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Event -> {
                holder as MainEventViewHolder
                holder.bookName.text = item.baseObject.title
                (item.baseObject.producerName + " ${item.baseObject.producerPatronymic}" +
                        " ${item.baseObject.producerSurname}"
                        + " / " + " ${item.baseObject.title}").also {
                    holder.bookDescription.text = it
                }
                imageLoader.loadBookCover(item.baseObject.bookCover, holder.coverBook)
            }
            is TimeSeparator -> {
                holder as TimeSeparatorViewHolder
                holder.title.text = item.title
            }
            else -> {
                holder as UnknownTypeViewHolder
                holder.title.text = "ID: $position, type: ${item.javaClass.simpleName}"
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (val item = items[holder.adapterPosition]) {
            is Event -> {
                holder as MainEventViewHolder
                holder.cardView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, item.baseObject)
                    holder.itemView.findNavController()
                        .navigate(R.id.nav_book_details_fragment, bundle)
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is MainEventViewHolder -> {
                holder.cardView.setOnClickListener(null)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}