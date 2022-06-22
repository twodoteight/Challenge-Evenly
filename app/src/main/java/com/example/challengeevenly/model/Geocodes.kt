package com.example.challengeevenly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Data class for geocodes
@Parcelize
data class Geocodes (
    val main: Coordinate,
    val roof: Coordinate? = null
) : Parcelable