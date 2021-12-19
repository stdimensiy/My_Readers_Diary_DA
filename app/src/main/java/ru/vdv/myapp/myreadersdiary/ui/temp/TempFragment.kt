package ru.vdv.myapp.myreadersdiary.ui.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.vdv.myapp.myreadersdiary.databinding.FragmentTemporaryFragmentBinding

class TempFragment : Fragment() {

    private lateinit var tempViewModel: TempViewModel
    private var _binding: FragmentTemporaryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tempViewModel =
            ViewModelProvider(this).get(TempViewModel::class.java)

        _binding = FragmentTemporaryFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textReadingProcess
        tempViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        // инициализация
        binding.buttonGet.setOnClickListener { tempViewModel.testGet() }
        binding.buttonPost.setOnClickListener { tempViewModel.testPost() }
        binding.buttonPatch.setOnClickListener { tempViewModel.testPatch() }
        binding.buttonDelete.setOnClickListener { tempViewModel.testDelete() }
        binding.buttonPut.setOnClickListener { tempViewModel.testPut() }
        binding.buttonHead.setOnClickListener { tempViewModel.testHead() }
        binding.buttonOption.setOnClickListener { tempViewModel.testOptions() }
        binding.buttonHttp.setOnClickListener { tempViewModel.testHttp() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}