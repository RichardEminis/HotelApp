package com.example.hotelapp.ui.hotelsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.model.Hotel
import com.example.hotelapp.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HotelsListState(
    val hotels: List<Hotel> = emptyList()
)

@HiltViewModel
class HotelsListViewModel @Inject constructor(private val hotelRepository: HotelRepository) :
    ViewModel() {
    private val _hotelsList = MutableLiveData(HotelsListState())
    val hotelsList: LiveData<HotelsListState>
        get() = _hotelsList

    fun loadHotels() {
        viewModelScope.launch {
            try {
                val hotels = hotelRepository.getHotels()
                _hotelsList.value = hotelsList.value?.copy(hotels = hotels)
            } catch (e: Exception) {
                Log.e("HotelsListViewModel", "Error loading hotels", e)
            }
        }
    }

    fun sortHotelsByDistance() {
        val sortedHotels = _hotelsList.value?.hotels?.sortedBy { it.distance }
        _hotelsList.value = sortedHotels?.let { _hotelsList.value?.copy(hotels = it) }
    }

    fun sortHotelsByRooms() {
        val sortedHotels =
            _hotelsList.value?.hotels?.sortedByDescending { it.getAvailableSuitesCount() }
        _hotelsList.value = sortedHotels?.let { _hotelsList.value?.copy(hotels = it) }
    }
}