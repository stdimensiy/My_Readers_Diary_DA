package ru.vdv.myapp.myreadersdiary.ui.settings

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.ui.common.BaseConstants

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var fab: FloatingActionButton

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab2(view)
    }

    private fun setupFab(){
        Log.d("Моя проверка", "Сработал FAB 1")
        fab = activity?.let {it.findViewById(R.id.fab)}!!
        val avd = { iconRes: Int ->
            AppCompatResources.getDrawable(
                requireContext(),
                iconRes
            ) as AnimatedVectorDrawable
        }
        val icon = avd(R.drawable.ic_cached_to_read_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
    }

    private fun setupFab2(view: View){
        // режим готовности к возврату из настроек в главный экран
        Log.d("Моя проверка", "Сработал FAB 2")
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
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.nav_main)
        }
    }
}