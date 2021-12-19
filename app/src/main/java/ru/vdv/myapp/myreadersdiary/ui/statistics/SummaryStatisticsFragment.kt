package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.FragmentSummaryStatisticsBinding
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader

class SummaryStatisticsFragment : Fragment() {

    private val imageLoader = GlideImageLoader()
    private var _binding: FragmentSummaryStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ActivityStatisticsGraphAdapter
    private lateinit var summaryStatisticsViewModel: SummaryStatisticsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = ActivityStatisticsGraphAdapter()
        summaryStatisticsViewModel =
            ViewModelProvider(this)[SummaryStatisticsViewModel::class.java]

        _binding = FragmentSummaryStatisticsBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textStatistics
//        summaryStatisticsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //запрос данных пользователя подписка на результат
        summaryStatisticsViewModel.currentUser.observe(viewLifecycleOwner, Observer {
            setImageAvatar(it.avatarUrl)
            setCustomBackgroundImage(it.backgroundUrl)
        })

        val eventsGraph = binding.rvEventsGraph
        eventsGraph.adapter = adapter
        eventsGraph.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        summaryStatisticsViewModel.prepareEventList.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            Log.d("Моя проверка", "Обновление запущено $it")
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setImageAvatar(url: String): Unit =
        with(binding) {
            imageLoader.loadUserAvatar(url, ivUserAvatar)
            Log.d("Моя проверка", "HomeViewHolder  / сработал setImageAvatar")
        }

    fun setCustomBackgroundImage(url: String): Unit =
        with(binding) {
            imageLoader.loadUserBackground(url, ivUserCustomBgImage)
            Log.d("Моя проверка", "HomeViewHolder  / сработал setImageAvatar")
        }
}