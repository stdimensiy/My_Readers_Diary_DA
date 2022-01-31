package ru.vdv.myapp.myreadersdiary.ui.books

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentListOfBooksBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class ListOfBooksFragment : BaseFragment<FragmentListOfBooksBinding>() {
    private lateinit var ofBooksAdapter: ListOfBooksAdapter
    private lateinit var viewModel: ListOfBooksViewModel
    private lateinit var fab: FloatingActionButton
    private val avd = { iconRes: Int ->
        AppCompatResources.getDrawable(
            requireContext(),
            iconRes
        ) as AnimatedVectorDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ofBooksAdapter = ListOfBooksAdapter()
        viewModel = ViewModelProvider(this).get(ListOfBooksViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfBooks = binding.listOfBooks
        listOfBooks.adapter = ofBooksAdapter
        listOfBooks.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.prepareItems.observe(viewLifecycleOwner) {
            it?.let {
                ofBooksAdapter.items = it
                ofBooksAdapter.notifyDataSetChanged()
                setFabStateReady(view)
            }
        }
        fetchData()
    }

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        setFabStateLoading()
    }

    private fun setFabStateLoading() {
        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            //установка лиссенера на повторное извлечение даных
            fetchData()
        }
    }

    private fun setFabStateReady(view: View) {
        val icon = avd(R.drawable.ic_cached_to_add_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.nav_create_new_book_Fragment)
        }
    }

    private fun fetchData() {
        viewModel.fetchData()
    }
}