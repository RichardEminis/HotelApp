package com.example.hotelapp.ui.hotelsList

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelapp.R
import com.example.hotelapp.model.Hotel

class HotelsListAdapter(
    private var itemClickListener: OnItemClickListener? = null,
): RecyclerView.Adapter<HotelsListAdapter.ViewHolder>()  {

    var dataSet: List<Hotel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(hotelId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hotelItem: CardView = itemView.findViewById(R.id.cvHotelItem)
        var hotelImage: ImageView = itemView.findViewById(R.id.ivHotelImage)
        var hotelName: TextView = itemView.findViewById(R.id.tvHotelName)
        var hotelRooms: TextView = itemView.findViewById(R.id.tvHotelAvailableRooms)
        var hotelAddress: TextView = itemView.findViewById(R.id.tvHotelAddress)
        var hotelDistance: TextView = itemView.findViewById(R.id.tvHotelDistance)
        var hotelStars: RatingBar = itemView.findViewById(R.id.ratingItemBarStars)
        val context: Context = view.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = dataSet[position]
        holder.hotelName.text = hotel.name
        holder.hotelAddress.text = hotel.address
        holder.hotelDistance.text = hotel.distance.toString()
        holder.hotelRooms.text = hotel.getAvailableSuitesCount().toString()
        holder.hotelStars.rating = hotel.stars
        holder.hotelItem.setOnClickListener {
            itemClickListener?.onItemClick(hotel.id)
        }

        Glide.with(holder.context)
            .load(dataSet[position].image)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(holder.hotelImage)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}