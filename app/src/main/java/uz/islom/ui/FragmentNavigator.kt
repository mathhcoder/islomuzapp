package uz.islom.ui

import uz.islom.model.app.FunctionType
import uz.islom.model.app.OptionType
import uz.islom.model.db.Mosque
import uz.islom.model.db.Site

interface FragmentNavigator {
    fun navigateToAuthorization()
    fun navigateToRegistration()
    fun navigateToMain()
    fun navigateToFunction(function: FunctionType)
    fun navigateToOption(optionType: OptionType)
    fun navigateToSalats()
    fun navigateToSite(site: Site)

    fun navigateToMosqueInfo(mosque: Mosque)

}