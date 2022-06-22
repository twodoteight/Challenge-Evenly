package com.example.challengeevenly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Data class for categories
@Parcelize
data class Category (
    val id: Long,
    val name: String,
    val icon: Icon
) : Parcelable
