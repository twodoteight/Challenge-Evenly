package com.example.challengeevenly.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//Data class for places
@Parcelize
data class Place (
    @SerializedName("fsq_id")
    val fsqID: String,
    val categories: List<Category>,
//    val chains: List<String>,
//    val distance: Long,
    val geocodes: Geocodes,
//    val link: String,
    val location: Location,
    val name: String,
//    val timezone: String
) : Parcelable