package com.example.hotelapp.ui.hotel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelBinding
import com.example.hotelapp.ui.hotelsList.HotelsListFragment
import com.example.hotelapp.utils.ARG_HOTEL_ID
import com.example.hotelapp.utils.HOTEL_DETAIL_URL

class HotelFragment : Fragment() {

    private val binding: FragmentHotelBinding by lazy {
        FragmentHotelBinding.inflate(layoutInflater)
    }

    private val viewModel: HotelViewModel by viewModels()
    private var hotelId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelId = arguments?.getInt(ARG_HOTEL_ID) ?: 0

        viewModel.loadHotel(hotelId)

        initUI()
    }

    private fun initUI() {
        viewModel.hotelUiState.observe(viewLifecycleOwner) { state ->
            state?.let { item ->

                binding.textAddressDescription.text = item.hotel?.address
                binding.textDistanceCount.text = item.hotel?.distance?.toString()
                binding.ratingBarStars.rating = item.hotel?.stars ?: 0f
                binding.hotelText.text = item.hotel?.name
                item.hotel?.suitesAvailability?.let { suites ->
                    displaySuitesAvailability(suites)
                }

                item.hotel?.image?.let { imageUrl ->
                    Glide.with(this)
                        .load(HOTEL_DETAIL_URL + imageUrl)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(binding.hotelImage)
                }

                binding.btnBack.setOnClickListener {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, HotelsListFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun displaySuitesAvailability(suites: String) {
        val suitesArray = suites.split(":")
        binding.linearLayoutSuites.removeAllViews()

        for (suite in suitesArray) {
            if (suite.isNotBlank()) {
                val textView = TextView(requireContext())
                textView.text = suite
                textView.setPadding(16, 8, 16, 8)
                textView.background = resources.getDrawable(R.drawable.rounded_border, null)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(8, 8, 8, 8)
                textView.layoutParams = layoutParams
                binding.linearLayoutSuites.addView(textView)
            }
        }
    }
}