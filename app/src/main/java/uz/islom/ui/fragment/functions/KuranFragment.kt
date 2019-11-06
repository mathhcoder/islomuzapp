package uz.islom.ui.fragment.functions

//import sun.swing.SwingUtilities2.drawRect
import android.graphics.*
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.R
import uz.islom.ext.*
import uz.islom.ext.paging.CurlPage
import uz.islom.ext.paging.CurlView
import uz.islom.ui.base.BaseFragment


class KuranFragment : BaseFragment() {

    companion object {
        fun newInstance() = KuranFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return FrameLayout(inflater.context).apply {

            var index = 0

            addView(CurlView(context).apply {
                id = R.id.viewPager
                setPageProvider(PageProvider())
                setSizeChangedObserver(SizeChangedObserver())
                currentIndex = index
            }, FrameLayout.LayoutParams(full, full))


            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    private inner class PageProvider : CurlView.PageProvider {

        // Bitmap resources.
        private val mBitmapIds = intArrayOf(R.drawable.page1, R.drawable.page2, R.drawable.page3, R.drawable.page4, R.drawable.page5)

        override fun getPageCount(): Int {
            return 5
        }

        private fun loadBitmap(width: Int, height: Int, index: Int): Bitmap {
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            b.eraseColor(-0x1)
            val c = Canvas(b)
            val d = drawable(mBitmapIds[index])!!

            val margin = 7
            val border = 3
            val r = Rect(margin, margin, width - margin, height - margin)

            var imageWidth = r.width() - border * 2
            var imageHeight = imageWidth * d.intrinsicHeight / d.intrinsicWidth
            if (imageHeight > r.height() - border * 2) {
                imageHeight = r.height() - border * 2
                imageWidth = imageHeight * d.intrinsicWidth / d.intrinsicHeight
            }

            r.left += (r.width() - imageWidth) / 2 - border
            r.right = r.left + imageWidth + border + border
            r.top += (r.height() - imageHeight) / 2 - border
            r.bottom = r.top + imageHeight + border + border

            val p = Paint()
            p.color = -0x3f3f40
            c.drawRect(r, p)
            r.left += border
            r.right -= border
            r.top += border
            r.bottom -= border

            d.bounds = r
            d.draw(c)

            return b
        }

        override fun updatePage(page: CurlPage, width: Int, height: Int, index: Int) {

            when (index) {
                // First case is image on front side, solid colored back.
                0 -> {

                    val front = requireContext().drawTextToBitmap(
                            "\nﭑ ﭒ ﭓ ﭔ ﭕ" +
                                    "\nﭖ ﭗ ﭘ" +
                                    "\nﭙ ﭚ ﭛ ﭜ ﭝ" +
                                    "\nﭞ ﭟ ﭠ ﭡ ﭢ",
                            activity?.screenWidth() ?: 0,
                            (activity?.screenHeight() ?: 0) - dp(56), Color.BLACK)

                    page.setTexture(front, CurlPage.SIDE_BOTH)
                    page.setColor(Color.rgb(251, 239, 219), CurlPage.SIDE_FRONT)
                    page.setColor(Color.parseColor("#D8D3D6"), CurlPage.SIDE_BACK)
                }
                // Second case is image on back side, solid colored front.
                1 -> {
                    val front = loadBitmap(width, height, 1)
                    page.setTexture(front, CurlPage.SIDE_BOTH)
                    page.setColor(Color.parseColor("#FBF0DA"), CurlPage.SIDE_FRONT)
                    page.setColor(Color.parseColor("#D8D3D6"), CurlPage.SIDE_BACK)
                }
                // Third case is images on both sides.
                2 -> {
                    val front = loadBitmap(width, height, 2)
                    // val back = loadBitmap(width, height, 3)
                    page.setTexture(front, CurlPage.SIDE_BOTH)
                    page.setColor(Color.parseColor("#FBF0DA"), CurlPage.SIDE_FRONT)
                    page.setColor(Color.parseColor("#D8D3D6"), CurlPage.SIDE_BACK)
                }
                // Fourth case is images on both sides - plus they are blend against
                // separate colors.
                3 -> {
                    val front = loadBitmap(width, height, 3)
                    //  val back = loadBitmap(width, height, 1)
                    page.setTexture(front, CurlPage.SIDE_BOTH)
                    page.setColor(Color.parseColor("#FBF0DA"), CurlPage.SIDE_FRONT)
                    page.setColor(Color.parseColor("#D8D3D6"), CurlPage.SIDE_BACK)
                    //  page.setTexture(back, CurlPage.SIDE_BACK)
                    //   page.setColor(Color.argb(127, 170, 130, 255), CurlPage.SIDE_FRONT)
                    //   page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK)
                }
                // Fifth case is same image is assigned to front and back. In this
                // scenario only one texture is used and shared for both sides.
                4 -> {
                    val front = loadBitmap(width, height, 4)
                    page.setTexture(front, CurlPage.SIDE_BOTH)
                    page.setColor(Color.parseColor("#FBF0DA"), CurlPage.SIDE_FRONT)
                    page.setColor(Color.parseColor("#D8D3D6"), CurlPage.SIDE_BACK)
                    //    page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK)
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        view?.findViewById<CurlView>(R.id.viewPager)?.apply {
            onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<CurlView>(R.id.viewPager)?.apply {
            onResume()
        }
    }

    /**
     * CurlView size changed observer.
     */
    private inner class SizeChangedObserver : CurlView.SizeChangedObserver {
        override fun onSizeChanged(w: Int, h: Int) {

            view?.findViewById<CurlView>(R.id.viewPager)?.apply {
                if (w > h) {
                    setViewMode(CurlView.SHOW_TWO_PAGES)
                    setMargins(.1f, .05f, .1f, .05f)
                } else {
                    setViewMode(CurlView.SHOW_ONE_PAGE)
                    //  setMargins(.1f, .1f, .1f, .1f)
                }
            }
        }
    }

    fun onRetainNonConfigurationInstance(): Any {
        return view?.findViewById<CurlView>(R.id.viewPager)?.currentIndex!!
    }

}