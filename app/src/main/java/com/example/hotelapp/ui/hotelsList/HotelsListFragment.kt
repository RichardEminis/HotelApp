package com.example.hotelapp.ui.hotelsList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelsListBinding
import com.example.hotelapp.utils.NO_NETWORK
import com.example.hotelapp.utils.RETRY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelsListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private val viewModel: HotelsListViewModel by viewModels()
    private lateinit var adapter: HotelsListAdapter

    private var _binding: FragmentHotelsListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        setupSpinner()

        if (isInternetAvailable()) {
            viewModel.loadHotels()
        } else {
            showNoInternetSnackbar()
        }

        viewModel.hotelsList.observe(viewLifecycleOwner) { hotelsListState ->
            adapter.dataSet = hotelsListState.hotels
        }
    }

    private fun openHotelById(hotelId: Int) {
        viewModel.getHotelById(hotelId)?.let {
            findNavController().navigate(
                HotelsListFragmentDirections.actionHotelsListFragmentToHotelFragment(
                    hotelId
                )
            )
        }
    }

    private fun initRecycler() {
        adapter = HotelsListAdapter()
        recyclerView = binding.rvHotels
        recyclerView?.adapter = adapter

        adapter.setOnItemClickListener(object :
            HotelsListAdapter.OnItemClickListener {
            override fun onItemClick(hotelId: Int) {
                openHotelById(hotelId)
            }
        })
    }

    private fun setupSpinner() {
        val spinner = binding.spinnerFilter
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> viewModel.loadHotels()
                    1 -> viewModel.sortHotelsByDistance()
                    2 -> viewModel.sortHotelsByRooms()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetSnackbar() {
        Snackbar.make(binding.root, NO_NETWORK, Snackbar.LENGTH_INDEFINITE)
            .setAction(RETRY) {
                if (isInternetAvailable()) {
                    viewModel.loadHotels()
                } else {
                    showNoInternetSnackbar()
                }
            }
            .show()
    }
}