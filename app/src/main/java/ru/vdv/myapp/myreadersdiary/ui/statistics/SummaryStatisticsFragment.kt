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
import ru.vdv.myapp.myreadersdiary.databinding.FragmentSummaryStatisticsBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class SummaryStatisticsFragment : BaseFragment<FragmentSummaryStatisticsBinding>() {
    private lateinit var adapter: ActivityStatisticsGraphAdapter
    private lateinit var viewModel: SummaryStatisticsViewModel
    private lateinit var fab: FloatingActionButton
    private val avd = { iconRes: Int -> AppCompatResources.getDrawable(
        requireContext(),
        iconRes
    ) as AnimatedVectorDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ActivityStatisticsGraphAdapter()
        viewModel =
            ViewModelProvider(this)[SummaryStatisticsViewModel::class.java]
        viewModel.fetchCurrentUser(
            PreferenceManager.getDefaultSharedPreferences(context).getString(
                getString(R.string.spref_key_login),
                getString(R.string.spref_key_login_default)
            )
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                setImageAvatar(it.avatarUrl)
                setCustomBackgroundImage(it.backgroundUrl)
                setFabStateReady(view)
            }
        }

        val eventsGraph = binding.rvEventsGraph
        eventsGraph.adapter = adapter
        eventsGraph.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewModel.prepareEventList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            eventsGraph.scrollToPosition(adapter.itemCount - 1)
        }
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
            //установка лиссенера на повторное извлечение даных
            fetchData()
        }
    }

    private fun setFabStateReady(view: View) {
        val icon = avd(R.drawable.ic_cached_to_back_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.nav_new_main_fragment)
        }
    }

    private fun fetchData(){
        viewModel.fetchCurrentUser(
            PreferenceManager.getDefaultSharedPreferences(context).getString(
                getString(R.string.spref_key_login),
                getString(R.string.spref_key_login_default)
            )
        )
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