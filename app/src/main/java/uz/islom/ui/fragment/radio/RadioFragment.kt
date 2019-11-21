package uz.islom.ui.fragment.radio

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
import uz.islom.ext.*
import uz.islom.model.dm.Theme
import uz.islom.model.enums.OptionType
import uz.islom.ui.BaseActivity
import uz.islom.ui.custom.BaseImageButton
import uz.islom.ui.custom.BaseTextView
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ui.cell.OptionCell


//class RadioFragment : SwipeAbleFragment() {
//
//    companion object {
//        fun newInstance() = RadioFragment()
//    }
//
//    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,appTheme: Theme): View? {
//        return FrameLayout(inflater.context).apply {
//
//            setBackgroundColor(colour(R.color.white))
//
//            addView(FrameLayout(context).apply {
//
//                addView(BaseImageButton(context).apply {
//                    id = R.id.idBackButton
//                    setButtonPadding(dp(16))
//                }, ViewGroup.LayoutParams(dp(56), dp(56)))
//
//                addView(BaseTextView(context).apply {
//                    id = R.id.idTextView
//                    gravity = Gravity.CENTER_VERTICAL
//                    text = string(R.string.radio)
//                    setTextSizeSp(18)
//                }, FrameLayout.LayoutParams(full, full).apply {
//                    leftMargin = dp(72)
//                    rightMargin = dp(16)
//                })
//
//            }, ViewGroup.LayoutParams(full, dp(56)))
//
//            addView(LinearLayout(context).apply {
//
//                orientation = LinearLayout.HORIZONTAL
//                gravity = Gravity.END
//
//
//                addView(BaseTextView(context).apply {
//                    id = R.id.textView
//                    gravity = Gravity.CENTER_VERTICAL
//                    text = "Mishary Alafasiy - Jundulloh"
//                    setTextSizeSp(18)
//                }, LinearLayout.LayoutParams(0, full, 1f).apply {
//                    rightMargin = dp(16)
//                    leftMargin = dp(16)
//                })
//
//                addView(MaterialButton(context).apply {
//                    id = R.id.liveButton
//                    text = "live"
//                    isAllCaps = true
//                }, FrameLayout.LayoutParams(wrap, dp(56), Gravity.END))
//
//                addView(BaseImageButton(context).apply {
//                    id = R.id.playButton
//                    setButtonPadding(dp(16))
//                }, FrameLayout.LayoutParams(dp(56), dp(56), Gravity.END))
//
//
//            }, FrameLayout.LayoutParams(full, dp(56), Gravity.BOTTOM))
//
//            layoutParams = ViewGroup.LayoutParams(full, full)
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<BaseImageButton>(R.id.idBackButton).apply {
//            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
//            setOnClickListener {
//                fragmentManager?.popBackStack()
//            }
//        }
//
//        view.findViewById<BaseImageButton>(R.id.playButton).apply {
//            setImageResources(R.drawable.ic_play, colour(R.color.colorPrimary))
//        }
//
//    }
//
//    inner class PlayListAdapter : RecyclerView.Adapter<PlayListHolder>() {
//
//        var newItemsUpdate: List<OptionType> = ArrayList()
//            setAlarm(value) {
//                field = value
//                notifyDataSetChanged()
//            }
//
//        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayListHolder = PlayListHolder(OptionCell(p0.context).apply {
//            layoutParams = ViewGroup.LayoutParams(full, dp(64))
//        })
//
//        override fun onViewAttachedToWindow(holder: PlayListHolder) {
//            super.onViewAttachedToWindow(holder)
//            holder.itemView.setOnClickListener {
//                newItemsUpdate.getOrNull(holder.adapterPosition)?.let {
//                    (activity as? BaseActivity)?.navigationManager?.navigateToOption(it)
//                }
//            }
//        }
//
//        override fun getItemCount() = newItemsUpdate.size
//
//        override fun onBindViewHolder(p0: PlayListHolder, p1: Int) {
//            p0.bindFunction(newItemsUpdate[p1])
//        }
//    }
//
//    inner class PlayListHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bindFunction(function: OptionType) {
//            (itemView as? OptionCell)?.optionImage = drawable(function.imageRes)
//            (itemView as? OptionCell)?.optionName = string(function.nameRes) ?: ""
//        }
//    }
//
//}