package uz.islom.model.dm

data class GeoPoint(val lat: Float,
                    val lng: Float){
    companion object{
        val DEFAULT = GeoPoint(41.2995f, 69.2401f)
    }
}