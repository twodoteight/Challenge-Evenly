package com.example.challengeevenly.repository

import android.util.Log
import com.example.challengeevenly.api.FoursquareService
import com.example.challengeevenly.core.Result
import com.example.challengeevenly.model.Photo
import com.example.challengeevenly.model.Place
import com.example.challengeevenly.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class that manages API operations
 */
@Singleton
class FoursquareRepository @Inject constructor(
    private val foursquareService: FoursquareService
    ) {

    private val defaultCoordinates = "${Constants.LATITUDE},${Constants.LONGITUDE}"
    private val defaultPlaceLimit = Constants.PLACE_LIMIT
    private val defaultPhotoLimit = Constants.PHOTO_LIMIT
    private val defaultRadius = Constants.PLACE_SEARCH_RADIUS

    suspend fun getNearbyPlaces(): Result<List<Place>> {
        println("Getting nearby places on thread: ${Thread.currentThread()}")
        return try {
            val result = foursquareService.getNearbyPlaceData(defaultCoordinates, defaultPlaceLimit)
            println("Nearby places received on thread: ${Thread.currentThread()}, returning results")
            Result.Success(result.results)
        } catch (error: Throwable) {
            Result.Failure(error, "Nearby place data could not be received")
        }
    }

    suspend fun getPlaces(): Result<List<Place>> {
        println("Getting places on thread: ${Thread.currentThread()}")
        return try {
            val result = foursquareService.getPlaceData(defaultCoordinates, defaultRadius, defaultPlaceLimit)
            println("Places received on thread: ${Thread.currentThread()}, returning results")
            Result.Success(result.results)
        } catch (error: Throwable) {
            Result.Failure(error, "Place data could not be received")
        }
    }
    suspend fun getPlacePhotos(fsqId: String): Result<List<Photo>> {
        println("Getting photos on thread: ${Thread.currentThread()}")
        return try {
            val result = foursquareService.getPlacePhotos(fsqId, defaultPhotoLimit)
            println("Photos received on thread: ${Thread.currentThread()}, returning results")
            Result.Success(result)
        } catch (error: Throwable) {
            Result.Failure(error, "Photos could not be fetched")
        }
    }
}