package uz.islom.ui.screens.functions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import uz.islom.R
import uz.islom.model.Option
import uz.islom.ui.FragmentNavigator
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.ui.custom.MenuButton
import uz.islom.util.*


class RadioFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = RadioFragment()
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(inflater.context).apply {

            setBackgroundColor(colour(R.color.white))

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.backButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER_VERTICAL
                    text = string(R.string.radio)
                    setTextSizeSp(18)
                }, FrameLayout.LayoutParams(full, full).apply {
                    leftMargin = dp(72)
                    rightMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, dp(56)))

            addView(LinearLayout(context).apply {

                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.END


                addView(BaseTextView(context).apply {
                    id = R.id.textView
                    gravity = Gravity.CENTER_VERTICAL
                    text = "Mishary Alafasiy - Jundulloh"
                    setTextSizeSp(18)
                }, LinearLayout.LayoutParams(0, full, 1f).apply {
                    rightMargin = dp(16)
                    leftMargin = dp(16)
                })

                addView(MaterialButton(context).apply {
                    id = R.id.liveButton
                    text = "live"
                    isAllCaps = true
                }, FrameLayout.LayoutParams(wrap, dp(56), Gravity.END))

                addView(BaseImageButton(context).apply {
                    id = R.id.playButton
                    setButtonPadding(dp(16))
                }, FrameLayout.LayoutParams(dp(56), dp(56), Gravity.END))


            }, FrameLayout.LayoutParams(full, dp(56), Gravity.BOTTOM))

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

        view.findViewById<BaseImageButton>(R.id.playButton).apply {
            setImageResources(R.drawable.ic_play, colour(R.color.colorPrimary))
        }

    }

    inner class PlayListAdapter : RecyclerView.Adapter<PlayListHolder>() {

        var data: List<Option> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayListHolder = PlayListHolder(MenuButton(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(64))
        })

        override fun onViewAttachedToWindow(holder: PlayListHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {
                data.getOrNull(holder.adapterPosition)?.let {
                    (activity as? FragmentNavigator)?.navigateToOption(it)
                }
            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: PlayListHolder, p1: Int) {
            p0.bindFunction(data[p1])
        }
    }

    inner class PlayListHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFunction(function: Option) {
            (itemView as? MenuButton)?.imageRes = function.imageRes
            (itemView as? MenuButton)?.textRes = function.nameRes
        }
    }

}