package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentStatisticsBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.OnViewReady

class SummaryStatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    private lateinit var adapter: SummaryStatisticAdapter
    private lateinit var eventGraphAdapter: ActivityStatisticsGraphAdapter
    private lateinit var viewModel: SummaryStatisticsViewModel
    private lateinit var fab: FloatingActionButton
    private val avd = { iconRes: Int ->
        AppCompatResources.getDrawable(
            requireContext(),
            iconRes
        ) as AnimatedVectorDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = SummaryStatisticAdapter()
        eventGraphAdapter = ActivityStatisticsGraphAdapter()
        viewModel = ViewModelProvider(this)[SummaryStatisticsViewModel::class.java]
        viewModel.fetchCurrentUser(
            context?.let {
                PreferenceManager.getDefaultSharedPreferences(it).getString(
                    getString(R.string.spref_key_login), getString(R.string.spref_key_login_default)
                )
            }
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statisticFragmentList = binding.list
        statisticFragmentList.adapter = adapter
        statisticFragmentList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter.setOnViewReadyListener(
            object : OnViewReady {
                override fun onViewReady() {
                    val eventsGraph = adapter.eventGraph
                    eventsGraph.adapter = eventGraphAdapter
                    eventsGraph.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    viewModel.prepareEventList.observe(viewLifecycleOwner) {
                        eventGraphAdapter.items = it
                        eventGraphAdapter.notifyDataSetChanged()
                        setFabStateReady(view)
                        eventsGraph.scrollToPosition(adapter.itemCount - 1)
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        setFabStateLoading()
    }

    private fun setFabStateLoading() {
        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            //установка слушателя на повторное извлечение данных
            fetchData()
        }
    }

    private fun setFabStateReady(view: View) {
        val icon = avd(R.drawable.ic_cached_to_back_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.nav_main)
        }
    }

    private fun fetchData() {
        viewModel.fetchCurrentUser(
            context?.let {
                PreferenceManager.getDefaultSharedPreferences(it).getString(
                    getString(R.string.spref_key_login),
                    getString(R.string.spref_key_login_default)
                )
            }
        )
    }
}