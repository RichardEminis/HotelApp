package com.example.hotelapp.ui.hotelsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelsListBinding
import com.example.hotelapp.ui.hotel.HotelFragment
import com.example.hotelapp.utils.ARG_HOTEL_ID

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


        viewModel.loadHotels()

        viewModel.hotelsList.observe(viewLifecycleOwner) { hotelsListState ->
            adapter.dataSet = hotelsListState.hotels
        }
    }

    private fun openHotelById(hotelId: Int) {
        viewModel.getHotelById(hotelId)?.let {
            val bundle = bundleOf(ARG_HOTEL_ID to hotelId)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<HotelFragment>(R.id.mainContainer, args = bundle)
            }
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
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.loadHotels()
                    1 -> viewModel.sortHotelsByDistance()
                    2 -> viewModel.sortHotelsByRooms()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}