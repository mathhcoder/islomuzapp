package uz.islom.ext.compass

class MovingAverageList(private val max: Int = 10) : ArrayList<Float>() {

    private val average: Float?
        get() {
            var sum = 0.0f
            val it = this.iterator()
            while (it.hasNext()) {
                val v = it.next()
                sum += v
            }
            return sum / this.size
        }

    override fun add(element: Float): Boolean {
        if (this.size >= max) {
            this.removeAt(0)
        }
        return super.add(element)
    }

    fun addAndGetAverage(element: Float): Float? {
        this.add(element)
        return this.average
    }
}