package com.judahben149.taskmakerjudah.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.judahben149.taskmakerjudah.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val themeMode = preferenceManager.findPreference<Preference>(getString(R.string.key_app_theme)) as ListPreference
        themeMode.setOnPreferenceChangeListener { _, newValue ->
            val mode: Int = when(newValue) {
                "light" -> MODE_NIGHT_NO
                "dark" -> MODE_NIGHT_YES
                else -> MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(mode)
            true
        }
    }

    private fun updateTheme(mode: Int): Boolean {
        setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }
}