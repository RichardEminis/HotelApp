package com.example.hotelapp.ui.hotelsList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelapp.R
import com.example.hotelapp.databinding.ItemHotelBinding
import com.example.hotelapp.model.Hotel

class HotelsListAdapter(
    private var itemClickListener: OnItemClickListener? = null,
) : RecyclerView.Adapter<HotelsListAdapter.ViewHolder>() {

    var dataSet: List<Hotel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(hotelId: Int)
    }

    class ViewHolder(private val binding: ItemHotelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel, clickListener: OnItemClickListener?) {
            binding.tvHotelName.text = hotel.name
            binding.tvHotelAddress.text = hotel.address
            binding.tvHotelDistance.text = hotel.distance.toString()
            binding.tvHotelAvailableRooms.text = hotel.getAvailableSuitesCount().toString()
            binding.ratingItemBarStars.rating = hotel.stars
            binding.cvHotelItem.setOnClickListener {
                clickListener?.onItemClick(hotel.id)
            }

            Glide.with(itemView.context)
                .load(hotel.image ?: R.drawable.img_error)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(binding.ivHotelImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = dataSet[position]
        holder.bind(hotel, itemClickListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}