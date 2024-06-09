package com.example.hotelapp.ui.hotelsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.repository.HotelRepository
import com.example.hotelapp.model.Hotel
import kotlinx.coroutines.launch

data class HotelsListState(
    val hotels: List<Hotel> = emptyList()
)

class HotelsListViewModel (application: Application) : AndroidViewModel(application) {
    private val _hotelsList = MutableLiveData(HotelsListState())
    val hotelsList: LiveData<HotelsListState>
        get() = _hotelsList

    private val repository = HotelRepository()

    fun loadHotels() {
        viewModelScope.launch {
            val hotels = repository.getHotels()
            _hotelsList.value = hotelsList.value?.copy(hotels = hotels)
        }
    }
}
