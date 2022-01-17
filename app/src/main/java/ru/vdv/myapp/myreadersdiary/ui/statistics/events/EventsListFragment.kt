package ru.vdv.myapp.myreadersdiary.ui.statistics.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.EventsListFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class EventsListFragment : BaseFragment<EventsListFragmentBinding>() {
    private lateinit var adapter: EventsListAdapter
    private lateinit var listViewModel: EventsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = EventsListAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfEvent = binding.rvEventsList
        listOfEvent.adapter = adapter
        listOfEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listViewModel = ViewModelProvider(this)[EventsListViewModel::class.java]
        listViewModel.prepareEventList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }
    }
}