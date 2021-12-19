package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.vdv.myapp.myreadersdiary.databinding.FragmentSummaryStatisticsBinding

class SummaryStatisticsFragment : Fragment() {

    private lateinit var summaryStatisticsViewModel: SummaryStatisticsViewModel
    private var _binding: FragmentSummaryStatisticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        summaryStatisticsViewModel =
            ViewModelProvider(this)[SummaryStatisticsViewModel::class.java]

        _binding = FragmentSummaryStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textStatistics
//        summaryStatisticsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}