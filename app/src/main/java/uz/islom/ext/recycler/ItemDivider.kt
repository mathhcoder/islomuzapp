package uz.islom.ext.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.ext.colour
import uz.islom.ext.dp
import uz.islom.ext.drawable
import uz.islom.ext.getColorWithAlpha
import uz.islom.model.dm.Theme

class ItemDivider(context: Context, theme: Theme, private val padding: Int) : RecyclerView.ItemDecoration() {

    private val lineDivider = ShapeDrawable().apply {
        paint.color = theme.tertiaryColor.getColorWithAlpha(0.13f)
        intrinsicHeight = context.dp(1)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {


        val left = padding
        val right = parent.width - padding

        val childCount = parent.childCount

        for (i in 0 until childCount) {

            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + lineDivider.intrinsicHeight

            lineDivider.setBounds(left, top, right, bottom)
            lineDivider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = lineDivider.intrinsicHeight
    }
}
