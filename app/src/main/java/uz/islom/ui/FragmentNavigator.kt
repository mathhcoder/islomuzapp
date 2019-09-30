package uz.islom.ui

import uz.islom.model.Functions
import uz.islom.model.Option
import uz.islom.model.Site

interface FragmentNavigator {
    fun navigateToAuthorization()
    fun navigateToRegistration()
    fun navigateToMain()
    fun navigateToFunction(function: Functions)
    fun navigateToOption(option: Option)
    fun navigateToSalats()
    fun navigateToSite(site: Site)
}