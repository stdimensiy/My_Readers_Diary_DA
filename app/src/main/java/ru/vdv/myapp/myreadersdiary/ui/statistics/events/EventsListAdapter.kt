package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.domain.Event
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToMainList

class EventsListAdapter : RecyclerView.Adapter<EventsListViewHolder>() {
    var items: List<ToMainList> = listOf()
    val imageLoader: ImageLoader<ImageView> = GlideImageLoader()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return EventsListViewHolder(root)
    }

    override fun onBindViewHolder(holder: EventsListViewHolder, position: Int) {
        val item = items[position] as Event
        holder.titleEvent.text = item.title
        holder.fixDataEvent.text = item.representedDateTime.toString()
        (item.baseObject.producerName + " ${item.baseObject.producerPatronymic}" +
                " ${item.baseObject.producerSurname}"
                + " / " + " ${item.baseObject.title}").also {
            holder.eventBody.text = it
        }
//        imageLoader.loadBookCover(item.baseObject.bookCover, holder.coverBook)
    }

    override fun onViewAttachedToWindow(holder: EventsListViewHolder) {
        val item = items[holder.adapterPosition] as Event
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, item.baseObject)
            holder.itemView.findNavController().navigate(R.id.nav_book_details_fragment, bundle)
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: EventsListViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}