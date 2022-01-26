package ru.vdv.myapp.myreadersdiary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.EventListItemBinding
import ru.vdv.myapp.myreadersdiary.databinding.ItemListTimeSeparatorBinding
import ru.vdv.myapp.myreadersdiary.databinding.NewEventListItemBinding
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.domain.TimeSeparator
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import ru.vdv.myapp.myreadersdiary.ui.common.BaseViewType
import ru.vdv.myapp.myreadersdiary.ui.common.ToMainList

class MainEventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<ToMainList> = listOf()
    val imageLoader: ImageLoader<ImageView> = GlideImageLoader()

    override fun getItemViewType(position: Int) = when (items[position]) {
        is Event -> BaseViewType.USUAL
        is TimeSeparator -> BaseViewType.TIME_SEPARATOR
        else -> BaseViewType.UNKNOWN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType){
            BaseViewType.TIME_SEPARATOR -> {
                val binding =
                    ItemListTimeSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainTimeSeparatorViewHolder(binding)
            }
            BaseViewType.USUAL -> {
                val binding =
                    NewEventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainEventViewHolder(binding)
            }
            else -> {
                // временно, нужно сделать разметку и холдер для подобного рода ошибок
                val binding =
                    NewEventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainEventViewHolder(binding)
            }
        }
    }

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
                holder as MainTimeSeparatorViewHolder
                holder.title.text = item.title
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        when (val item = items[holder.adapterPosition]) {
            is Event -> {
                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, item.baseObject)
                    holder.itemView.findNavController()
                        .navigate(R.id.nav_book_details_fragment, bundle)
                }
            }
            is TimeSeparator -> {
                // лиссенер не назначается
            }
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}