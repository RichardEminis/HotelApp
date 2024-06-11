package com.example.hotelapp.ui.hotelsList

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.hotelapp.model.Hotel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelapp.R

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
        holder.hotelName.text = dataSet[position].name
        holder.hotelAddress.text = dataSet[position].address
        holder.hotelRooms.text = dataSet[position].suitesAvailability.toString()
        holder.hotelItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
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