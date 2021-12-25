package ru.vdv.myapp.myreadersdiary.ui.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import ru.vdv.myapp.myreadersdiary.databinding.FragmentTemporaryFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class TempFragment : BaseFragment<FragmentTemporaryFragmentBinding>() {
    private lateinit var tempViewModel: TempViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempViewModel =
            ViewModelProvider(this).get(TempViewModel::class.java)

        val textView: TextView = binding.textReadingProcess
        tempViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.buttonGet.setOnClickListener { tempViewModel.testGet() }
        binding.buttonPost.setOnClickListener { tempViewModel.testPost() }
        binding.buttonPatch.setOnClickListener { tempViewModel.testPatch() }
        binding.buttonDelete.setOnClickListener { tempViewModel.testDelete() }
        binding.buttonPut.setOnClickListener { tempViewModel.testPut() }
        binding.buttonHead.setOnClickListener { tempViewModel.testHead() }
        binding.buttonOption.setOnClickListener { tempViewModel.testOptions() }
        binding.buttonHttp.setOnClickListener { tempViewModel.testHttp() }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}