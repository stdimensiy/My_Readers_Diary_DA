package ru.vdv.myapp.myreadersdiary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.IImageLoader

class MainEventsAdapter : RecyclerView.Adapter<MainEventViewHolder>() {
    var items: List<Event> = listOf()
    val imageLoader: IImageLoader<ImageView> = GlideImageLoader()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainEventViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return MainEventViewHolder(root)
    }

    override fun onBindViewHolder(holder: MainEventViewHolder, position: Int) {
        val item = items[position]
        holder.titleEvent.text = item.title
        holder.fixDataEvent.text = item.representedDateTime.toString()
        (item.baseObject.producerName + " ${item.baseObject.producerPatronymic}" +
                " ${item.baseObject.producerSurname}"
                + " / " + " ${item.baseObject.title}").also {
            holder.eventBody.text = it
        }
        imageLoader.loadBookCover(
            "https://dadapproves.ru/usercontent/book/covers/${item.baseObject.bookCover}",
            holder.coverBook
        )
    }

    override fun onViewAttachedToWindow(holder: MainEventViewHolder) {
        val item = items[holder.adapterPosition]
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("ARG_BOOK", item)
            holder.itemView.findNavController().navigate(R.id.bookDetailsFragment, bundle)
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: MainEventViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}