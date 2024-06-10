package com.example.hotelapp.ui.hotelsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelsListBinding
import com.example.hotelapp.utils.ARG_HOTEL_ADDRESS
import com.example.hotelapp.utils.ARG_HOTEL_ID
import com.example.hotelapp.utils.ARG_HOTEL_IMAGE_URL
import com.example.hotelapp.utils.ARG_HOTEL_NAME

class HotelsListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private lateinit var viewModel: HotelsListViewModel
    private lateinit var adapter: HotelsListAdapter

    private var _binding: FragmentHotelsListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private var hotelName: String? = null
    private var hotelImageUrl: String? = null
    private var hotelId: Int? = 0
    private var hotelAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hotelId = requireArguments().getInt(ARG_HOTEL_ID)
        //hotelName = requireArguments().getString(ARG_HOTEL_NAME)
        //hotelAddress = requireArguments().getString(ARG_HOTEL_ADDRESS)
        //hotelImageUrl = requireArguments().getString(ARG_HOTEL_IMAGE_URL)

        viewModel = ViewModelProvider(this)[HotelsListViewModel::class.java]

        initRecycler()

        viewModel.loadHotels()

        viewModel.hotelsList.observe(viewLifecycleOwner) { hotelsListState ->
            adapter.dataSet = hotelsListState.hotels
        }
    }



    private fun initRecycler() {
        adapter = HotelsListAdapter()
        recyclerView = binding.rvHotels
        recyclerView?.adapter = adapter

    }
}