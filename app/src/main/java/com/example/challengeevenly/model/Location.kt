package com.example.challengeevenly.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//Data class for locations
@Parcelize
data class Location (
    val address: String? = null,
    val country: String,
    @SerializedName("cross_street")
    val crossStreet: String? = null,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    val locality: String,
    val postcode: String,
    val region: String,
    val neighborhood: List<String>? = null
) : Parcelable