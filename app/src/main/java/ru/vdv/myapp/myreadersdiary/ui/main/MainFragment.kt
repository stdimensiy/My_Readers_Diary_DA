package ru.vdv.myapp.myreadersdiary.ui.main

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.MainFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class MainFragment : BaseFragment<MainFragmentBinding>() {
    private lateinit var adapter: MainEventsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
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
        adapter = MainEventsAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        fetchData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        bottomAppBar = requireActivity().findViewById(R.id.bottomAppBar)
        setFabStateLoading()
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
            setFabStateReady(view)
        }

        //запрос данных пользователя подписка на результат
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Log.d(TAG, "Пользователь НЕ определен!")
                //bottomAppBar.replaceMenu(R.menu.bottom_app_bar_main_for_user_not_authorized)
                bottomAppBar.replaceMenu(R.menu.bottom_app_bar_main)
            } else {
                bottomAppBar.replaceMenu(R.menu.bottom_app_bar_main)
                Log.d(TAG, "Пользователь определен: ${it.name}")
            }
        })
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
        val icon = avd(R.drawable.ic_cached_to_add_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.nav_create_new_book_Fragment)
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