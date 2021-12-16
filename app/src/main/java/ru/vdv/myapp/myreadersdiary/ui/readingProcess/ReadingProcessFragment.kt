package ru.vdv.myapp.myreadersdiary.ui.readingProcess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.vdv.myapp.myreadersdiary.databinding.FragmentReadingProcessBinding

class ReadingProcessFragment : Fragment() {

    private lateinit var readingProcessViewModel: ReadingProcessViewModel
    private var _binding: FragmentReadingProcessBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        readingProcessViewModel =
            ViewModelProvider(this).get(ReadingProcessViewModel::class.java)

        _binding = FragmentReadingProcessBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textReadingProcess
        readingProcessViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        // инициализация
        binding.buttonGet.setOnClickListener { readingProcessViewModel.testGet() }
        binding.buttonPost.setOnClickListener { readingProcessViewModel.testPost() }
        binding.buttonPatch.setOnClickListener { readingProcessViewModel.testPatch() }
        binding.buttonDelete.setOnClickListener { readingProcessViewModel.testDelete() }
        binding.buttonPut.setOnClickListener { readingProcessViewModel.testPut() }
        binding.buttonHead.setOnClickListener { readingProcessViewModel.testHead() }
        binding.buttonOption.setOnClickListener { readingProcessViewModel.testOptions() }
        binding.buttonHttp.setOnClickListener { readingProcessViewModel.testHttp() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}