package com.example.challengeevenly.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Photo (
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val prefix: String,
    val suffix: String,
    val width: Long,
    val height: Long
)