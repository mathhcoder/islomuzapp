package uz.islom.android.compass

import android.content.Context
import android.hardware.*
import kotlin.math.abs

class CompassListener(context: Context, latitude: Float, longitude: Float, altitude: Float) : SensorEventListener {

    private val listeners = ArrayList<CompassAssistantListener>()

    private val sensorManager: SensorManager?
    private val accelerometer: Sensor?
    private val magnetometer: Sensor?

    private val rotationMatrix = FloatArray(9)
    private val orientation = FloatArray(3)

    private var lastAccelerometer = FloatArray(3)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometerSet = false
    private var lastMagnetometerSet = false
    private var currentDegree = 0f
    private var currentSmoothedDegree = 0f
    private var currentAccuracy = 0

    private var declination = 0.0f

    private var movingAverageList = MovingAverageList(10)

    init {
        val geomagneticField = GeomagneticField(latitude, longitude, altitude, System.currentTimeMillis())

        declination = geomagneticField.declination

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    }

    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor == this.accelerometer) {
            System.arraycopy(event.values, 0, this.lastAccelerometer, 0, event.values.size)
            this.lastAccelerometerSet = true
        } else if (event.sensor == this.magnetometer) {
            System.arraycopy(event.values, 0, this.lastMagnetometer, 0, event.values.size)
            this.lastMagnetometerSet = true

        }

        if (this.lastAccelerometerSet && this.lastAccelerometerSet) {
            SensorManager.getRotationMatrix(this.rotationMatrix, null, this.lastAccelerometer, this.lastMagnetometer)
            SensorManager.getOrientation(this.rotationMatrix, this.orientation)


            val azimuthInRadians = this.orientation[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()

            this.currentDegree = cleanDegrees(azimuthInDegrees + declination)

            informListenersAboutNewDegree(this.currentDegree)

            this.currentSmoothedDegree = this.movingAverageList.addAndGetAverage(this.currentDegree)!!
            informListenersAboutNewSmoothedDegree(this.currentSmoothedDegree)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        if (sensor == magnetometer) {
            this.currentAccuracy = accuracy
        }
    }

    fun addListener(listener: CompassAssistantListener) {
        this.listeners.add(listener)
    }

    fun start() {
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager?.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
        for (listener in listeners) {
            listener.onCompassStarted()
        }
    }

    fun stop() {
        this.sensorManager?.unregisterListener(this, this.accelerometer)
        this.sensorManager?.unregisterListener(this, this.magnetometer)
        this.lastAccelerometer = FloatArray(3)
        this.lastMagnetometer = FloatArray(3)
        this.lastAccelerometerSet = false
        this.lastMagnetometerSet = false
        this.currentAccuracy = 0
        this.currentDegree = 0f
        this.currentSmoothedDegree = 0f
        this.movingAverageList = MovingAverageList(10)
        for (l in this.listeners) {
            l.onCompassStopped()
        }
    }


    private fun cleanDegrees(degree: Float): Float {
        val difference = abs(currentDegree - degree)
        return if (difference > 180) {
            degree + if (currentDegree >= 0) 360 else -360
        } else {
            degree
        }
    }

    private fun informListenersAboutNewDegree(degree: Float) {
        for (l in this.listeners) {
            l.onNewDegreesToNorth(-degree)
        }
    }

    private fun informListenersAboutNewSmoothedDegree(degree: Float) {
        for (l in this.listeners) {
            l.onNewSmoothedDegreesToNorth(-degree)
        }
    }

}
