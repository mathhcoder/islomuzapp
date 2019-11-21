package uz.islom.model.entity

data class Madhab(val asrShadow: Float) {

    companion object {
        val HANAFI = Madhab(2f)
        val MALIKI = Madhab(1f)
        val SHAFII = Madhab(1f)
        val HANBALI = Madhab(1f)
    }
}