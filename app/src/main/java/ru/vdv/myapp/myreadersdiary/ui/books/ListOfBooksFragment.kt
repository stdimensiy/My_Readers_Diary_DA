package ru.vdv.myapp.myreadersdiary.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.FragmentListOfBooksBinding

class ListOfBooksFragment : Fragment() {

    private lateinit var ofBooksAdapter: ListOfBooksAdapter
    private lateinit var listOfBooksViewModel: ListOfBooksViewModel
    private var _binding: FragmentListOfBooksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ofBooksAdapter = ListOfBooksAdapter()
        listOfBooksViewModel =
            ViewModelProvider(this).get(ListOfBooksViewModel::class.java)

        _binding = FragmentListOfBooksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfBooks = binding.listOfBooks
        listOfBooks.adapter = ofBooksAdapter
        listOfBooks.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listOfBooksViewModel.prepareItems.observe(viewLifecycleOwner, Observer {
            ofBooksAdapter.items = it
            ofBooksAdapter.notifyDataSetChanged()
        })

        listOfBooksViewModel.postResult.observe(viewLifecycleOwner, Observer {
            Log.d("Моя проверка", "Фрагмент. Результат получен $it")
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}