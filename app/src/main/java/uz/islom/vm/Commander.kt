package uz.islom.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.islom.IslomUzApp

class IslomUzViewModelProvider() : ViewModelProvider(IslomUzApp.getInstance(), object : ViewModelProvider.AndroidViewModelFactory(IslomUzApp.getInstance()) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}) {

    override fun <T : ViewModel?> get(modelClass: Class<T>): T {
        return super.get(modelClass)
    }

    override fun <T : ViewModel?> get(key: String, modelClass: Class<T>): T {
        return super.get(key, modelClass)
    }

}