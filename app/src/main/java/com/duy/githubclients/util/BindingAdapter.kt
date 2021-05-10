package com.duy.githubclients.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:errorRes")
fun setErrorMessage(view: TextView, resError: Int?): Unit {
    view.setResError(resError)
}

@BindingAdapter("avatar")
fun loadAvatar(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(it).apply(RequestOptions().circleCrop())
            .into(view)
    }
}

@BindingAdapter("visible")
fun visibitity(view: View, isVisible: Boolean) {
    if (isVisible)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}