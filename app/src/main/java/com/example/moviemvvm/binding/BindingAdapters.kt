package com.example.moviemvvm.binding

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindingImageFromUrl(view: ImageView, imageUrl:String?) {
    val prefix = "https://image.tmdb.org/t/p/w500"
    if(!imageUrl.isNullOrEmpty()) {
        val final_url = prefix + imageUrl
        Glide.with(view.context)
            .load(final_url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}