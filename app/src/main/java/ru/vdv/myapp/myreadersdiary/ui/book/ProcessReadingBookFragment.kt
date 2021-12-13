package ru.vdv.myapp.myreadersdiary.ui.book

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vdv.myapp.myreadersdiary.R

class ProcessReadingBookFragment : Fragment() {

    companion object {
        fun newInstance() = ProcessReadingBookFragment()
    }

    private lateinit var viewModel: ProcessReadingBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.process_reading_book_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcessReadingBookViewModel::class.java)
        // TODO: Use the ViewModel
    }

}