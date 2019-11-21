package uz.islom.vm

import uz.islom.model.dm.Salat
import uz.islom.model.repository.SalatRepository
import java.util.*

class SalatViewModel : BaseViewModel() {

    private val userPreference = preferenceManager.getUserPreference()
    private val adjustmentPreference = preferenceManager.getAdjustmentPreference()
    private val salatRepository = SalatRepository(userPreference, adjustmentPreference)

    fun getSalatTimes(date: Calendar): ArrayList<Salat> {
        return salatRepository.getSalatTimes(date)
    }


}