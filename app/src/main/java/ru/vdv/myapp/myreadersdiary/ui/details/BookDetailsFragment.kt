package ru.vdv.myapp.myreadersdiary.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.vdv.myapp.myreadersdiary.databinding.BookDetailsFragmentBinding
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.IImageLoader

class BookDetailsFragment : Fragment() {

    private var _binding: BookDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var book: Book
    private val imageLoader: IImageLoader<ImageView> = GlideImageLoader()

    companion object {
        fun newInstance() = BookDetailsFragment()
    }

    private lateinit var viewModel: BookDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        viewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}