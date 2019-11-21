package uz.islom.ui.fragment.asma

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ui.custom.HeaderLayout

class AsmaUlHusnaDescriptionFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance(asmaUlHusna: AsmaUlHusna) = AsmaUlHusnaDescriptionFragment().apply {
            arguments = Bundle().apply {
                putSerializable("asmaUlHusna",asmaUlHusna)
            }
        }
    }

    private val asmaUlHusna by lazy {
        arguments?.getSerializable("asmaUlHusna") as? AsmaUlHusna
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FrameLayout(inflater.context).apply {

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                theme = appTheme
                title = asmaUlHusna?.name?.uz
                setUpBackAction {
                    fragmentManager?.popBackStack()
                }
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(ScrollView(context).apply {

                addView(LinearLayout(context).apply {

                    orientation = LinearLayout.VERTICAL

                    addView(BaseTextView(context).apply {
                        typeface = font(R.font.scheherazade_normal)
                        gravity = Gravity.CENTER
                        text = asmaUlHusna?.name?.ar
                        setTextColor(appTheme.tertiaryColor)
                        setTextSizeSp(64)
                    },FrameLayout.LayoutParams(full, dp(196)))

                    addView(BaseTextView(context).apply {
                        text = asmaUlHusna?.description?.uz
                        setTextColor(appTheme.tertiaryColor)
                        setPadding(dp(16),0,dp(16),dp(16))
                    },FrameLayout.LayoutParams(full, wrap))


                },FrameLayout.LayoutParams(full, wrap))

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })


            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

}