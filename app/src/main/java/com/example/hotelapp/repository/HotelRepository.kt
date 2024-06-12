package com.example.hotelapp.repository

import com.example.hotelapp.model.Hotel
import com.example.hotelapp.model.HotelDetail
import com.example.hotelapp.network.ApiService
import com.example.hotelapp.utils.HOTEL_DETAIL_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HotelRepository @Inject constructor(private val apiService: ApiService) {

    private val ioDispatcher: CoroutineContext = Dispatchers.IO

    suspend fun getHotels(): List<Hotel> {
        return withContext(ioDispatcher) {
            val hotels = apiService.getHotels()
            hotels.forEach { hotel ->
                val detail = getHotelDetails(hotel.id)
                hotel.image = HOTEL_DETAIL_URL + detail.image
            }
            hotels
        }
    }

    suspend fun getHotelDetails(hotelId: Int): HotelDetail {
        return withContext(ioDispatcher) {
            apiService.getHotelDetails(hotelId)
        }
    }
}