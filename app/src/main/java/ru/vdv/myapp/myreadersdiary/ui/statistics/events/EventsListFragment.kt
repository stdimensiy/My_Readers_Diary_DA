package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.EventsListFragmentBinding
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader

class EventsListFragment : Fragment() {
    private val imageLoader = GlideImageLoader()
    private var _binding: EventsListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventsListAdapter
    private lateinit var listViewModel: EventsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = EventsListAdapter()
        _binding = EventsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfEvent = binding.rvEventsList
        listOfEvent.adapter = adapter
        listOfEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listViewModel = ViewModelProvider(this)[EventsListViewModel::class.java]
        listViewModel.prepareEventList.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }

}