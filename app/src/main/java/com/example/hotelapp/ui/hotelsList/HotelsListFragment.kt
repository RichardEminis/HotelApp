package com.example.hotelapp.ui.hotelsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R

class HotelsListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private lateinit var viewModel: HotelsListViewModel
    private lateinit var adapter: HotelsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotels_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hotelsList.observe(viewLifecycleOwner) { hotelsListState ->
            adapter.dataSet = hotelsListState.hotels
        }
    }


}