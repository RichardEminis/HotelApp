package com.example.hotelapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cleverpumpkinhotel.HotelRepository
import com.example.cleverpumpkinhotel.model.Hotel

data class HotelsListState(
    val hotels: List<Hotel> = emptyList()
)

class HotelsListViewModel (application: Application) : AndroidViewModel(application) {
    private val _hotelsList = MutableLiveData(HotelsListState())
    val hotelsList: LiveData<HotelsListState>
        get() = _hotelsList

    private val repository = HotelRepository(application)
}
