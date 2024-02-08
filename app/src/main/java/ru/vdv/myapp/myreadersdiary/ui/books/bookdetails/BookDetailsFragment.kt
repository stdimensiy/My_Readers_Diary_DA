package ru.vdv.myapp.myreadersdiary.ui.books.bookdetails

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
import ru.vdv.myapp.myreadersdiary.databinding.BookDetailsFragmentBinding
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class BookDetailsFragment : BaseFragment<BookDetailsFragmentBinding>() {

    private lateinit var adapter: BookEventListAdapter
    private lateinit var book: Book
    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var fab: FloatingActionButton
    private val avd = { iconRes: Int ->
        AppCompatResources.getDrawable(
            requireContext(),
            iconRes
        ) as AnimatedVectorDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = BookEventListAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        book = arguments?.getParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY)!!
        binding.textViewBookDetailsTitle.text = book.title
        "${book.producerName} ${book.producerPatronymic} ${book.producerSurname}".also {
            binding.textViewBookDetailsAuthorsOfTheBook.text = it
        }
        imageLoader.loadBookCover(book.bookCover, binding.imageViewBookDetailsCover)

        binding.startReadBook.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, book)
            view.findNavController().navigate(R.id.nav_process_reading_book_fragment, bundle)
        }
        val listOfEvent = binding.rvEventsList
        listOfEvent.adapter = adapter
        listOfEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[BookDetailsViewModel::class.java]
        viewModel.prepareEventList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            setupFabReadReady(view)
        }
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
            //установка слушателя на повторное извлечение данных
            //временно, после отработки дизайна разметки реализовать
        }
    }

    private fun setupFabReadReady(view: View) {
        val icon = avd(R.drawable.ic_cached_to_read_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(BaseConstants.MY_BOOK_BUNDLE_KEY, book)
            view.findNavController().navigate(R.id.nav_process_reading_book_fragment, bundle)
        }
    }

}