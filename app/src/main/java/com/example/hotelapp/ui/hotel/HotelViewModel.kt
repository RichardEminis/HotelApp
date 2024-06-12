package com.example.hotelapp.ui.hotel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.model.HotelDetail
import com.example.hotelapp.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HotelUiState(
    val hotel: HotelDetail? = null
)

@HiltViewModel
class HotelViewModel @Inject constructor(private val hotelRepository: HotelRepository) :
    ViewModel() {
    private val _hotelUiState = MutableLiveData(HotelUiState())
    val hotelUiState: LiveData<HotelUiState>
        get() = _hotelUiState

    fun loadHotel(hotelId: Int) {
        viewModelScope.launch {
            val hotel = hotelRepository.getHotelDetails(hotelId)
            _hotelUiState.value = hotelUiState.value?.copy(hotel = hotel)
        }
    }
}