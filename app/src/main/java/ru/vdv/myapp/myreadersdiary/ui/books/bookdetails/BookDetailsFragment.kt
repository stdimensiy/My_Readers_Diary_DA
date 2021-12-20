package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.BookDetailsFragmentBinding
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader

class BookDetailsFragment : Fragment() {

    private var _binding: BookDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val imageLoader: ImageLoader<ImageView> = GlideImageLoader()
    private lateinit var adapter: BookEventListAdapter
    private lateinit var book: Book
    private lateinit var viewModel: BookDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = BookEventListAdapter()
        _binding = BookDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        book = arguments?.getParcelable("ARG_BOOK")!!
        binding.textViewBookDetailsTitle.text = book.title
        "${book.producerName} ${book.producerPatronymic} ${book.producerSurname}".also { binding.textViewBookDetailsAuthorsOfTheBook.text = it }
        imageLoader.loadBookCover(
            "https://dadapproves.ru/usercontent/book/covers/${book.bookCover}",
            binding.imageViewBookDetailsCover
        )
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