package com.example.challengeevenly.api
import com.example.challengeevenly.model.Photo
import com.example.challengeevenly.model.Place
import com.example.challengeevenly.model.PlacesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Service endpoints for interacting with the Foursquare API
 */
interface FoursquareService {

    @Headers(
        "Accept: application/json",
        "Authorization: fsq3iA02tmuBWON3iu9nejSyGDIOScb8BZvSYN3SLPFD62I="
    )
    @GET("/v3/places/nearby")
    suspend fun getNearbyPlaceData(@Query("ll") coordinates: String,
                                   @Query("limit") limit: Int): PlacesResponse

    @Headers(
        "Accept: application/json",
        "Authorization: fsq3iA02tmuBWON3iu9nejSyGDIOScb8BZvSYN3SLPFD62I="
    )
    @GET("/v3/places/search")
    suspend fun getPlaceData(@Query("ll") coordinates: String,
                                   @Query("radius") radius: Int,
                                    @Query("limit") limit: Int): PlacesResponse

    @Headers(
        "Accept: application/json",
        "Authorization: fsq3iA02tmuBWON3iu9nejSyGDIOScb8BZvSYN3SLPFD62I="
    )
    @GET("/v3/places/{id}")
    suspend fun getPlaceWithID(@Path("id") id: String): Place

    @Headers(
        "Accept: application/json",
        "Authorization: fsq3iA02tmuBWON3iu9nejSyGDIOScb8BZvSYN3SLPFD62I="
    )
    @GET("/v3/places/{id}/photos")
    suspend fun getPlacePhotos(@Path("id") id: String,
                               @Query("limit") limit: Int): List<Photo>
}