package ru.vdv.myapp.myreadersdiary.ui.books.editbook

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vdv.myapp.myreadersdiary.databinding.EditBookFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class EditBookFragment : BaseFragment<EditBookFragmentBinding>() {

    private lateinit var viewModel: EditBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditBookViewModel::class.java]
    }

}