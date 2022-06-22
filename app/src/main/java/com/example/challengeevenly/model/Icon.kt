package com.example.challengeevenly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Data class for icon names
@Parcelize
data class Icon (
    val prefix: String,
    val suffix: String
) : Parcelable