package ru.vdv.myapp.myreadersdiary.ui.books.readingprocess

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vdv.myapp.myreadersdiary.databinding.ProcessReadingBookFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class ProcessReadingBookFragment : BaseFragment<ProcessReadingBookFragmentBinding>() {

    private lateinit var viewModel: ProcessReadingBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcessReadingBookViewModel::class.java)
    }

}