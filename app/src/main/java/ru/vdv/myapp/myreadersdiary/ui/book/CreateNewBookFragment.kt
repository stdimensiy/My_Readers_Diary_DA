package ru.vdv.myapp.myreadersdiary.ui.book

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vdv.myapp.myreadersdiary.R

class CreateNewBookFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNewBookFragment()
    }

    private lateinit var viewModel: CreateNewBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_new_book_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateNewBookViewModel::class.java)
        // TODO: Use the ViewModel
    }
}