package ru.vdv.myapp.myreadersdiary.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.futured.donut.DonutSection
import ru.vdv.myapp.myreadersdiary.databinding.MainFragmentBinding
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.ui.home.HomeAdapter

class MainFragment : Fragment() {
    private val imageLoader = GlideImageLoader()
    private var _binding: MainFragmentBinding? = null
    private lateinit var adapter: MainEventsAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = MainEventsAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.fetchCurrentUser(
            PreferenceManager.getDefaultSharedPreferences(context).getString("login", "132")
        )

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
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
            setName(it.name)
            setImageAvatar(it.avatarUrl)
            setCustomBackgroundImage(it.backgroundUrl)
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

    fun setName(name: String): Unit = with(binding) {
        tvName.text = name
        Log.d("Моя проверка", "HomeViewHolder  / сработал setName")
    }

    fun setImageAvatar(url: String): Unit =
        with(binding) {
            imageLoader.loadInfo(url, ivUserAvatar)
            Log.d("Моя проверка", "HomeViewHolder  / сработал setImageAvatar")
        }

    fun setCustomBackgroundImage(url: String): Unit =
        with(binding) {
            imageLoader.loadBg(url, ivUserCustomBgImage)
            Log.d("Моя проверка", "HomeViewHolder  / сработал setImageAvatar")
        }

    fun setDonatChartData(section1: DonutSection, section2: DonutSection): Unit =
        with(binding) {
            donutView.cap = 5f
            donutView.gapAngleDegrees = -45f
            donutView.gapWidthDegrees = 60f
            donutView.submitData(listOf(section1, section2))
        }


}