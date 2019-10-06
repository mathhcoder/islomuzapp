package uz.islom.ui.screens.pager

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import uz.islom.model.media.CircleTransform
import uz.islom.R
import uz.islom.update.UpdateCenter
import uz.islom.update.UpdatePath
import uz.islom.io.subscribeKt
import uz.islom.model.app.OptionType
import uz.islom.model.db.User
import uz.islom.ui.util.AppTheme
import uz.islom.ui.FragmentNavigator
import uz.islom.ui.base.BaseFragment
import uz.islom.ui.custom.MenuButton
import uz.islom.ui.util.dp
import uz.islom.ui.util.full
import uz.islom.ui.util.setTextSizeSp

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment().apply {}
    }

    private val functionsAdapter by lazy {
        return@lazy OptionsAdapter().apply {
            data = OptionType.values().toMutableList()
        }
    }

    private var appTheme = AppTheme.GREEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return NestedScrollView(inflater.context).apply {

            addView(LinearLayout(context).apply {

                descendantFocusability = LinearLayout.FOCUS_BLOCK_DESCENDANTS
                orientation = LinearLayout.VERTICAL

                addView(AppCompatImageView(context).apply {
                    id = R.id.imageView
                }, LinearLayout.LayoutParams(dp(96), dp(96)).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    topMargin = dp(32)
                })

                addView(AppCompatTextView(context).apply {
                    id = R.id.textView
                    gravity = Gravity.CENTER
                    setTextSizeSp(22)
                    setTextColor(appTheme.tertiaryColor)
                }, LinearLayout.LayoutParams(full, dp(56)))


                addView(RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = functionsAdapter
                    overScrollMode = View.OVER_SCROLL_NEVER
                    isNestedScrollingEnabled = true
                }, ViewGroup.LayoutParams(full, full))

            }, ViewGroup.LayoutParams(full, full))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUser(view, User(0, "Javohirxon Qodiriy", "https://picsum.photos/200"))

        UpdateCenter
                .subscribeTo(UpdatePath.Users)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindUser(view, it)
                })
    }


    private fun bindUser(view: View, user: User) {
        view.findViewById<AppCompatImageView>(R.id.imageView)?.also {
            Picasso.get().load(user.image).transform(CircleTransform()).centerCrop().fit().into(it)
        }
        view.findViewById<AppCompatTextView>(R.id.textView)?.also {
            it.text = user.name
        }

    }


    inner class OptionsAdapter : RecyclerView.Adapter<OptionHolder>() {

        var data: List<OptionType> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OptionHolder = OptionHolder(MenuButton(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(64))
        })

        override fun onViewAttachedToWindow(holder: OptionHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {
                data.getOrNull(holder.adapterPosition)?.let {
                    (activity as? FragmentNavigator)?.navigateToOption(it)
                }
            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: OptionHolder, p1: Int) {
            p0.bindFunction(data[p1])
        }
    }

    inner class OptionHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFunction(function: OptionType) {
            (itemView as? MenuButton)?.imageRes = function.imageRes
            (itemView as? MenuButton)?.textRes = function.nameRes
        }
    }


}