package com.example.challengeevenly.places

import androidx.lifecycle.*
import com.example.challengeevenly.core.Result
import com.example.challengeevenly.model.Photo
import com.example.challengeevenly.model.Place
import com.example.challengeevenly.repository.FoursquareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repository: FoursquareRepository
) : ViewModel() {

    lateinit var selectedPlace: Place

    // Private reference to the ui state
    private val _placesListState: MutableStateFlow<PlacesListState> = MutableStateFlow(PlacesListState())
    // Expose mutable ui state to outer classes
    val placesListState: StateFlow<PlacesListState> = _placesListState

    // Private reference to the photo state
    private val _currentPhotosState: MutableLiveData<CurrentPhotosState> = MutableLiveData<CurrentPhotosState>()
    // Expose mutable ui state to outer classes
    val currentPhotosState: LiveData<CurrentPhotosState> = _currentPhotosState

    fun selectPlace(place: Place) {
        selectedPlace = place
    }

    fun fetchNearbyPlaces() = viewModelScope.launch(Dispatchers.IO) {
        _placesListState.value = PlacesListState(isLoading = true)
        when (val result = repository.getPlaces()) {
            is Result.Failure -> {
                _placesListState.value = PlacesListState(isLoading = false, error = "Loading Failed")
                println("${result.message}: ${result.error}")
            }
            is Result.Success -> {
                _placesListState.value = PlacesListState(isLoading = false, data = result.data)
            }
        }
    }

    fun fetchPlacePhotos(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentPhotosState.postValue(CurrentPhotosState(isLoading = true))
        when (val result = repository.getPlacePhotos(id)) {
            is Result.Failure -> {
                println("${result.message}: ${result.error}")
            }
            is Result.Success -> {
                _currentPhotosState.postValue(CurrentPhotosState(data = result.data))
            }
        }
    }

    data class PlacesListState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: List<Place> = emptyList()
    )

    data class CurrentPhotosState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: List<Photo> = emptyList()
    )
}