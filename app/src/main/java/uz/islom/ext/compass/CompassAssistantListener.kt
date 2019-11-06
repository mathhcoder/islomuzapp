package uz.islom.ext.compass

interface CompassAssistantListener {

    fun onNewDegreesToNorth(degrees: Float)

    fun onNewSmoothedDegreesToNorth(degrees: Float)

    fun onCompassStopped()

    fun onCompassStarted()

}