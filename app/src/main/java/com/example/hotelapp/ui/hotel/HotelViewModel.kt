package com.example.hotelapp.ui.hotel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.model.HotelDetail
import com.example.hotelapp.repository.HotelRepository
import kotlinx.coroutines.launch

data class HotelUiState(
    val hotel: HotelDetail? = null
)

class HotelViewModel (application: Application) : AndroidViewModel(application) {
    private val _hotelUiState = MutableLiveData(HotelUiState())
    val hotelUiState: LiveData<HotelUiState>
        get() = _hotelUiState

    private val repository = HotelRepository()

    fun loadHotel(hotelId: Int) {
        viewModelScope.launch {
            val hotel = repository.getHotelDetails(hotelId)
            _hotelUiState.value = hotelUiState.value?.copy(hotel = hotel)
        }
    }
}