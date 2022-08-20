package com.example.android.devbyteviewer.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.ui.DevByteAdapter

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}


@BindingAdapter("listData")								/**  COOL  **/
fun bindList(rv : RecyclerView, data : List<Video>?)
{
    val myAdapter = rv.adapter as DevByteAdapter
    myAdapter.submitList(data)
}

