package uz.islom.ext.media

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.squareup.picasso.Transformation

class BlurTransform(private val context: Context, radius: Int) : Transformation {

    companion object {
        private const val UP_LIMIT = 25
        private const val LOW_LIMIT = 1
    }

    private val blurRadius: Int

    init {

        when {
            radius < LOW_LIMIT -> this.blurRadius = LOW_LIMIT
            radius > UP_LIMIT -> this.blurRadius = UP_LIMIT
            else -> this.blurRadius = radius
        }
    }

    override fun transform(source: Bitmap): Bitmap {

        val blurredBitmap: Bitmap = Bitmap.createBitmap(source)

        val renderScript = RenderScript.create(context)

        val input = Allocation.createFromBitmap(renderScript,
                source,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SCRIPT)


        val output = Allocation.createTyped(renderScript, input.getType())

        val script = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript))

        script.setInput(input)
        script.setRadius(blurRadius.toFloat())

        script.forEach(output)
        output.copyTo(blurredBitmap)

        source.recycle()
        return blurredBitmap
    }

    override fun key(): String {
        return "blurred"
    }


}
