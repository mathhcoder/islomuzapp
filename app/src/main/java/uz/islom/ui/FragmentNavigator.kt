package uz.islom.ui

import uz.islom.model.Function
import uz.islom.model.Option
import uz.islom.model.Site

interface FragmentNavigator {
    fun navigateToFunction(function: Function)
    fun navigateToOption(option: Option)
    fun navigateToSite(site: Site)
}