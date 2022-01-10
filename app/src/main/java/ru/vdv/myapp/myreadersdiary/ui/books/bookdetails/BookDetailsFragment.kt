package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.BookDetailsFragmentBinding
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class BookDetailsFragment : BaseFragment<BookDetailsFragmentBinding>() {

    private lateinit var adapter: BookEventListAdapter
    private lateinit var book: Book
    private lateinit var viewModel: BookDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = BookEventListAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        book = arguments?.getParcelable("ARG_BOOK")!!
        binding.textViewBookDetailsTitle.text = book.title
        "${book.producerName} ${book.producerPatronymic} ${book.producerSurname}".also {
            binding.textViewBookDetailsAuthorsOfTheBook.text = it
        }
        imageLoader.loadBookCover(
            "https://dadapproves.ru/usercontent/book/covers/${book.bookCover}",
            binding.imageViewBookDetailsCover
        )

        binding.startReadBook.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("ARG_BOOK", book)
            view.findNavController().navigate(R.id.nav_process_reading_book_fragment, bundle)
        }
        val listOfEvent = binding.rvEventsList
        listOfEvent.adapter = adapter
        listOfEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[BookDetailsViewModel::class.java]
        viewModel.prepareEventList.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }
}