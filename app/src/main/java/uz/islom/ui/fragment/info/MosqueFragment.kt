package uz.islom.ui.fragment.info

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.R
import uz.islom.ext.colour
import uz.islom.model.db.Mosque
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.model.enums.ThemeType
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp

class MosqueFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance(mosque: Mosque) = MosqueFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Mosque::javaClass.name, mosque)
            }
        }
    }

    private val mosque by lazy {
        arguments?.getSerializable(Mosque::javaClass.name) as? Mosque
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,appTheme: ThemeType): View? {
        return FrameLayout(inflater.context).apply {

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.idBackButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.idTextView
                    gravity = Gravity.CENTER_VERTICAL
                    setTextSizeSp(18)
                }, FrameLayout.LayoutParams(full, full).apply {
                    leftMargin = dp(72)
                    rightMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, dp(56)))

            addView(FrameLayout(context).apply {

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })


            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<BaseImageButton>(R.id.idBackButton).apply {
            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
            setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        view.findViewById<BaseTextView>(R.id.idTextView).apply {
            text = mosque?.name
        }

    }
}