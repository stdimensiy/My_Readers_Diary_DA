package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vdv.myapp.myreadersdiary.databinding.FragmentSummaryStatisticsBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class SummaryStatisticsFragment : BaseFragment<FragmentSummaryStatisticsBinding>() {
    private lateinit var adapter: ActivityStatisticsGraphAdapter
    private lateinit var summaryStatisticsViewModel: SummaryStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ActivityStatisticsGraphAdapter()
        summaryStatisticsViewModel =
            ViewModelProvider(this)[SummaryStatisticsViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //запрос данных пользователя подписка на результат
        summaryStatisticsViewModel.currentUser.observe(viewLifecycleOwner) {
            setImageAvatar(it.avatarUrl)
            setCustomBackgroundImage(it.backgroundUrl)
        }

        val eventsGraph = binding.rvEventsGraph
        eventsGraph.adapter = adapter
        eventsGraph.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        summaryStatisticsViewModel.prepareEventList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            eventsGraph.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun setImageAvatar(url: String): Unit =
        with(binding) {
            imageLoader.loadUserAvatar(url, ivUserAvatar)
        }

    private fun setCustomBackgroundImage(url: String): Unit =
        with(binding) {
            imageLoader.loadUserBackground(url, ivUserCustomBgImage)
        }
}