package uz.islom.ui.fragment.main

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
import uz.islom.ext.media.CircleTransform
import uz.islom.R
import uz.islom.ext.drawable
import uz.islom.ext.string
import uz.islom.update.UpdateCenter
import uz.islom.update.UpdatePath
import uz.islom.ext.subscribeKt
import uz.islom.model.enums.OptionType
import uz.islom.model.entity.User
import uz.islom.ui.BaseActivity
import uz.islom.model.dm.Theme
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ui.cell.OptionCell
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment().apply {}
    }

    private val functionsAdapter by lazy {
        return@lazy OptionsAdapter().apply {
            data = OptionType.values().toMutableList()
        }
    }

    private var appTheme = Theme.GREEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return NestedScrollView(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(LinearLayout(context).apply {

                descendantFocusability = LinearLayout.FOCUS_BLOCK_DESCENDANTS
                orientation = LinearLayout.VERTICAL

                addView(AppCompatImageView(context).apply {
                    id = R.id.idImageView
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


        UpdateCenter.subscribeTo(UpdatePath.UserUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindUser(view, it)
                })
    }


    private fun bindUser(view: View, user: User) {
        view.findViewById<AppCompatImageView>(R.id.idImageView)?.also {
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

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OptionHolder = OptionHolder(OptionCell(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(64))
        })

        override fun onViewAttachedToWindow(holder: OptionHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {
                data.getOrNull(holder.adapterPosition)?.let {
                    (activity as? BaseActivity)?.navigationManager?.navigateToOption(it)
                }
            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: OptionHolder, p1: Int) {
            p0.bindOption(data[p1])
        }
    }

    inner class OptionHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindOption(option: OptionType) {
            (itemView as? OptionCell)?.optionImage = drawable(option.imageRes)
            (itemView as? OptionCell)?.optionName = string(option.nameRes) ?: ""
        }
    }


}