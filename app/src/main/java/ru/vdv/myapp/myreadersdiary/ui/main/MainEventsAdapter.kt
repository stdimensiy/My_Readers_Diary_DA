package ru.vdv.myapp.myreadersdiary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.EventListItemBinding
import ru.vdv.myapp.myreadersdiary.databinding.NewEventListItemBinding
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants

class MainEventsAdapter : RecyclerView.Adapter<MainEventViewHolder>() {
    var items: List<Event> = listOf()
    val imageLoader: ImageLoader<ImageView> = GlideImageLoader()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainEventViewHolder {
        val binding =
            NewEventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainEventViewHolder, position: Int) {
        val item = items[position]
        //holder.titleEvent.text = item.title
        //holder.fixDataEvent.text = item.representedDateTime.toString()
        holder.bookName.text = item.baseObject.title
        (item.baseObject.producerName + " ${item.baseObject.producerPatronymic}" +
                " ${item.baseObject.producerSurname}"
                + " / " + " ${item.baseObject.title}").also {
            //holder.eventBody.text = it
        }
        imageLoader.loadBookCover(item.baseObject.bookCover, holder.coverBook)
    }

    override fun onViewAttachedToWindow(holder: MainEventViewHolder) {
        val item = items[holder.adapterPosition]
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, item.baseObject)
            holder.itemView.findNavController().navigate(R.id.nav_book_details_fragment, bundle)
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