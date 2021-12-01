package ru.vdv.myapp.myreadersdiary.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.vdv.myapp.myreadersdiary.R

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadBookCover(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .placeholder(R.drawable.book_plug)
            .error(R.drawable.error_sign)
            .transition(DrawableTransitionOptions.withCrossFade(1500))
            .centerCrop()
            .into(container)
    }

}