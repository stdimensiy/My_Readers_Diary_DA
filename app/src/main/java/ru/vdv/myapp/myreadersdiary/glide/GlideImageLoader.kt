package ru.vdv.myapp.myreadersdiary.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.vdv.myapp.myreadersdiary.R

class GlideImageLoader : ImageLoader<ImageView> {
    override fun loadBookCover(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .placeholder(R.drawable.book_plug)
            .error(R.drawable.error_sign)
            .transition(DrawableTransitionOptions.withCrossFade(1500))
            .centerCrop()
            .into(container)
    }

    override fun loadUserAvatar(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.error_sign)
            .transition(DrawableTransitionOptions.withCrossFade(1500))
            .circleCrop()
            .into(container)
    }

    override fun loadUserBackground(url: String, container: ImageView) {
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(container.context)
            .load(url)
            //.placeholder(R.drawable.bg1_01_2)
            //.error(R.drawable.error_sign)
            .transition(DrawableTransitionOptions.withCrossFade(1500))
            .apply(options)
            //.circleCrop()
            .into(container)
    }
}