package uz.islom.ui

import uz.islom.model.Function
import uz.islom.model.Site

interface FragmentNavigator {
    fun navigateToFunction(function: Function)
    fun navigateToSite(site: Site)
}