package uz.islom.ui.fragment.zikr

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setPadding
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.FooterLayout
import uz.islom.ui.custom.HeaderLayout
import uz.islom.ui.custom.ZikrButton
import uz.islom.ui.custom.ZikrStateLayout
import uz.islom.ui.fragment.SwipeAbleFragment

class ZikrFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = ZikrFragment()
    }

    private val stateSize by lazy {
        return@lazy context?.headerSize() ?: 0
    }

    private val stateView by lazy {
        view?.findViewById<ZikrStateLayout>(R.id.idStateLayout)
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return FrameLayout(inflater.context).apply {

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                theme = appTheme
                title = string(R.string.tasbih)
                setUpBackAction {
                    fragmentManager?.popBackStack()
                }
                setUpThirdAction(R.drawable.ic_refresh) {

                }
                setUpSecondAction(R.drawable.ic_33) {

                }
                setUpFirstAction(R.drawable.ic_volume_high) {

                }
            }, ViewGroup.LayoutParams(full, dp(56)))

            addView(ZikrStateLayout(context).apply {
                id = R.id.idStateLayout
                theme = appTheme
                currentZikrText = "Barchasi : 3"

            }, FrameLayout.LayoutParams(full, stateSize).apply {
                topMargin = dp(56)
            })

            addView(FooterLayout(context).apply {
                id = R.id.idFooterLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))


            addView(FrameLayout(context).apply {

                addView(ZikrButton(context).apply {
                    count = 3
                    setPadding(dp(16))
                    theme = appTheme
                }, FrameLayout.LayoutParams(stateSize, stateSize, Gravity.CENTER))

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56) + stateSize
            })

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateView?.let {
            it.currentZikrImage = R.drawable.img_subhan_allah
            it.progressInfo = "3/33"
            it.zikrProgress = 3.toFloat() / 33
        }
    }


}