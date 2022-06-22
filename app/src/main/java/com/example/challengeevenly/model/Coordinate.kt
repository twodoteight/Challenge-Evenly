package com.example.challengeevenly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

//Data class for coordinates
@Parcelize
data class Coordinate (
    val latitude: Double,
    val longitude: Double
) : Parcelable