package uz.islom.model.preference

import androidx.preference.PreferenceManager
import uz.islom.IslomUzApp
import uz.islom.vm.BaseViewModel

internal fun BaseViewModel.getMosqueUrl(): String {
    return PreferenceManager.getDefaultSharedPreferences(IslomUzApp.getInstance()).getString("mosqueUrl", "")
            ?: ""
}

internal fun BaseViewModel.setMosqueUrl(url: String) {
    PreferenceManager.getDefaultSharedPreferences(IslomUzApp.getInstance()).edit().putString("mosqueUrl", url).apply()
}