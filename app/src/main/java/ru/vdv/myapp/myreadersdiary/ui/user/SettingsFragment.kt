package ru.vdv.myapp.myreadersdiary.ui.user

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.vdv.myapp.myreadersdiary.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}