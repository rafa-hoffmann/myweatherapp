package com.sonder.myweatherapp.common.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sonder.myweatherapp.core.common.BuildConfig

private val backendImageUrl = BuildConfig.BACKEND_IMAGE_URL

fun ImageView.loadImage(imageName: String) =
    Glide.with(this).load(backendImageUrl + imageName).centerCrop().into(this)
