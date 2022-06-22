package com.example.challengeevenly.util

import android.content.res.Resources.getSystem
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.challengeevenly.R
import com.example.challengeevenly.model.Place

// Converts a value in dp to px
fun Int.toPx(): Int = (this * getSystem().displayMetrics.density).toInt()
// Converts a value in px to dp
fun Int.toDp(): Int = (this / getSystem().displayMetrics.density).toInt()
// Allows Glide to be used directly on image views
fun ImageView.loadCircleCropped(imageAddress: String) {
    Glide.with(this)
        .load(imageAddress)
        .circleCrop()
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(this)
}
fun ImageView.loadStandart(imageAddress: String) {
    Glide.with(this)
        .load(imageAddress)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(this)
}