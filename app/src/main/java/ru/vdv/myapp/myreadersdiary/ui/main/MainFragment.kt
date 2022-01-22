package ru.vdv.myapp.myreadersdiary.ui.main

import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.futured.donut.DonutSection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.MainFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class MainFragment : BaseFragment<MainFragmentBinding>() {
    private lateinit var adapter: MainEventsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MainEventsAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        val listOfEvent = binding.listOfEvents
        listOfEvent.adapter = adapter
        listOfEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.prepareEventList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        //запрос данных пользователя подписка на результат
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                setName(it.name)
                setImageAvatar(it.avatarUrl)
                setCustomBackgroundImage(it.backgroundUrl)
            }
        })

        val section1 = DonutSection(
            name = "section_1",
            color = Color.parseColor("#FFB98E"),
            amount = 4f
        )

        val section2 = DonutSection(
            name = "section_2",
            color = Color.parseColor("#FB1D32"),
            amount = 1f
        )
        setDonatChartData(section1, section2)

    }

    private fun setName(name: String): Unit = with(binding) {
        tvName.text = name
    }

    private fun setImageAvatar(url: String): Unit =
        with(binding) {
            imageLoader.loadUserAvatar(url, ivUserAvatar)
        }

    private fun setCustomBackgroundImage(url: String): Unit =
        with(binding) {
            imageLoader.loadUserBackground(url, ivUserCustomBgImage)
        }

    private fun setDonatChartData(section1: DonutSection, section2: DonutSection): Unit =
        with(binding) {
            donutView.cap = 5f
            donutView.gapAngleDegrees = -45f
            donutView.gapWidthDegrees = 60f
            donutView.submitData(listOf(section1, section2))
        }
    private fun setupFab() {
//        val adv = { iconRes: Int -> getDrawable(requireContext(), iconRes) as AnimatedVectorDrawable}
//        fab = requireActivity().findViewById(R.id.fab)
//        val  icon = adv(R.drawable.ic_cached_rotate_black_24dp_adv)
//        fab.setImageDrawable(icon)
//        icon.start()
        fab = activity?.let {it.findViewById(R.id.fab)}!!
        val avd = { iconRes: Int ->
            AppCompatResources.getDrawable(
                requireContext(),
                iconRes
            ) as AnimatedVectorDrawable
        }
        val icon = avd(R.drawable.avd_edit_to_reply_all)
        fab.setImageDrawable(icon)
        icon.start()
    }
}