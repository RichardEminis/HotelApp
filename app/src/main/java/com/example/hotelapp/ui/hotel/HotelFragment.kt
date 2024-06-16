package com.example.hotelapp.ui.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelBinding
import com.example.hotelapp.utils.HOTEL_DETAIL_URL
import com.example.hotelapp.utils.HOTEL_MAP_URL
import com.example.hotelapp.utils.MAIN_HALF_INDENT
import com.example.hotelapp.utils.MAIN_INDENT
import com.example.hotelapp.utils.NO_NETWORK
import com.example.hotelapp.utils.RETRY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelFragment : Fragment() {

    private val binding: FragmentHotelBinding by lazy {
        FragmentHotelBinding.inflate(layoutInflater)
    }

    private val viewModel: HotelViewModel by viewModels()
    private val args: HotelFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hotelId: Int = args.hotelId

        if (isInternetAvailable()) {
            viewModel.loadHotel(hotelId)
        } else {
            showNoInternetSnackBar()
        }

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

                Glide.with(this)
                    .load(item.hotel?.image?.let { HOTEL_DETAIL_URL + it } ?: R.drawable.img_error)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .into(binding.hotelImage)

                binding.btnShowOnMap.setOnClickListener {
                    showHotelOnMap(item.hotel?.latitude, item.hotel?.longitude)
                }

                binding.btnBack.setOnClickListener {
                    findNavController().navigate(HotelFragmentDirections.actionHotelFragmentToHotelsListFragment())
                }

                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                    findNavController().navigateUp()
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
                textView.setPadding(MAIN_INDENT, MAIN_HALF_INDENT, MAIN_INDENT, MAIN_HALF_INDENT)
                textView.background = resources.getDrawable(R.drawable.rounded_border, null)
                textView.typeface = resources.getFont(R.font.montserrat_semi_bold)
                textView.setTextColor(resources.getColor(R.color.description_color, null))

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(
                    MAIN_HALF_INDENT,
                    MAIN_HALF_INDENT,
                    MAIN_HALF_INDENT,
                    MAIN_HALF_INDENT
                )
                textView.layoutParams = layoutParams
                binding.linearLayoutSuites.addView(textView)
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetSnackBar() {
        Snackbar.make(binding.root, NO_NETWORK, Snackbar.LENGTH_INDEFINITE)
            .setAction(RETRY) {
                if (isInternetAvailable()) {
                    viewModel.loadHotel(args.hotelId)
                } else {
                    showNoInternetSnackBar()
                }
            }
            .show()
    }

    private fun showHotelOnMap(hotelLatitude: Double?, hotelLongitude: Double?) {
        val uri = Uri.parse("$HOTEL_MAP_URL$hotelLatitude,$hotelLongitude")
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(browserIntent)
    }
}