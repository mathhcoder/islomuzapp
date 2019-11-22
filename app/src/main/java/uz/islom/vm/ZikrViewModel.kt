package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import uz.islom.model.dm.ZikrState

class ZikrViewModel : BaseViewModel() {

    val zikrStateData = MutableLiveData<ZikrState>()

    private val zikrPreference = preferenceManager.getZikrPreference()

    init {
        clear(zikrPreference.typeMax, zikrPreference.max, zikrPreference.reaction)
    }

    fun increment() {

        val type = zikrStateData.value?.type ?: 0
        val typeProgress = zikrStateData.value?.typeProgress ?: 0
        val typeMax = zikrStateData.value?.typeMax ?: 33

        val progress = zikrStateData.value?.progress ?: 0
        val max = zikrStateData.value?.max ?: 99

        val nextType = if (typeProgress >= typeMax) if (type == 2) 0 else type + 1 else type
        val nextTypeProgress = if (typeProgress >= typeMax) 1 else typeProgress + 1
        val nextProgress = if (progress >= max) 0 else progress + 1

        val reaction = zikrStateData.value?.reaction ?: 0

        zikrStateData.postValue(ZikrState(
                type = nextType,
                typeProgress = nextTypeProgress,
                typeMax = typeMax,
                progress = nextProgress,
                max = max,
                reaction = reaction
        ))
    }

    fun changeCount() {
        val max = zikrStateData.value?.max ?: 99
        val reaction = zikrStateData.value?.reaction ?: 0
        if (max == 99) {
            clear(11, 33,reaction)
        } else {
            clear(33, 99,reaction)
        }
    }

    fun changeReaction() {
        val reaction = zikrStateData.value?.reaction ?: 0
        val nextReaction = if (reaction == 2) 0 else reaction + 1

        zikrStateData.value?.let {
            zikrStateData.postValue(it.copy(reaction = nextReaction))
        }

    }

    fun clear() {
        zikrStateData.value?.let {
            clear(it.typeMax, it.max, it.reaction)
        }
    }

    private fun clear(typeMax: Int, max: Int, reaction: Int) {
        zikrStateData.postValue(ZikrState(type = 0,
                typeMax = typeMax,
                typeProgress = 0,
                progress = 0,
                max = max,
                reaction = reaction))
    }

}