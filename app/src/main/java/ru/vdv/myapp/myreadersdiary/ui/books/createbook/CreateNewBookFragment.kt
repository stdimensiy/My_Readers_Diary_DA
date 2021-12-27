package ru.vdv.myapp.myreadersdiary.ui.books.createbook

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vdv.myapp.myreadersdiary.databinding.CreateNewBookFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class CreateNewBookFragment : BaseFragment<CreateNewBookFragmentBinding>() {

    private lateinit var viewModel: CreateNewBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Тут будет размещена инициализация переменных фрагмента
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateNewBookViewModel::class.java]
    }
}