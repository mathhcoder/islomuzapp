package uz.islom.ui.screens.info

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.R
import uz.islom.android.colour
import uz.islom.model.db.Mosque
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.ui.util.dp
import uz.islom.ui.util.full
import uz.islom.ui.util.setTextSizeSp

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

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(inflater.context).apply {

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.backButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
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

        view.findViewById<BaseImageButton>(R.id.backButton).apply {
            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
            setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        view.findViewById<BaseTextView>(R.id.titleView).apply {
            text = mosque?.name
        }

    }
}