package ru.vdv.myapp.myreadersdiary.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.FragmentListOfBooksBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class ListOfBooksFragment : BaseFragment<FragmentListOfBooksBinding>() {
    private lateinit var ofBooksAdapter: ListOfBooksAdapter
    private lateinit var listOfBooksViewModel: ListOfBooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ofBooksAdapter = ListOfBooksAdapter()
        listOfBooksViewModel =
            ViewModelProvider(this).get(ListOfBooksViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfBooks = binding.listOfBooks
        listOfBooks.adapter = ofBooksAdapter
        listOfBooks.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listOfBooksViewModel.prepareItems.observe(viewLifecycleOwner) {
            ofBooksAdapter.items = it
            ofBooksAdapter.notifyDataSetChanged()
        }

        listOfBooksViewModel.postResult.observe(viewLifecycleOwner, Observer {
        })
    }
}