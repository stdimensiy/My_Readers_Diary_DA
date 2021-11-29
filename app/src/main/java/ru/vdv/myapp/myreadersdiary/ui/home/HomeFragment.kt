package ru.vdv.myapp.myreadersdiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var adapter: HomeAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        adapter = HomeAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfBooks = binding.listOfBooks
        listOfBooks.adapter = adapter
        listOfBooks.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeViewModel.prepareItems.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}