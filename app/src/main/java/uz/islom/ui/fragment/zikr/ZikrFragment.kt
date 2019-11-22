package uz.islom.ui.fragment.zikr

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uz.islom.IslomUzApp
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.ZikrState
import uz.islom.ui.custom.FooterLayout
import uz.islom.ui.custom.HeaderLayout
import uz.islom.ui.custom.ZikrButton
import uz.islom.ui.custom.ZikrStateLayout
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.vm.ZikrViewModel

class ZikrFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = ZikrFragment()
    }

    private val stateSize by lazy {
        return@lazy context?.headerSize() ?: 0
    }


    private val headerLayout by lazy {
        view?.findViewById<HeaderLayout>(R.id.idHeaderLayout)
    }

    private val stateView by lazy {
        view?.findViewById<ZikrStateLayout>(R.id.idStateLayout)
    }

    private val buttonView by lazy {
        view?.findViewById<ZikrButton>(R.id.idInfoView)
    }

    private val zikrViewModel by lazy {
        ViewModelProviders.of(this).get(ZikrViewModel::class.java)
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
                    zikrViewModel.clear()
                }
                setUpSecondAction(R.drawable.ic_33) {
                    zikrViewModel.changeCount()
                }
                setUpFirstAction(R.drawable.ic_volume_high) {
                    zikrViewModel.changeReaction()
                }
            }, ViewGroup.LayoutParams(full, dp(56)))

            addView(ZikrStateLayout(context).apply {
                id = R.id.idStateLayout
                theme = appTheme

            }, FrameLayout.LayoutParams(full, stateSize).apply {
                topMargin = dp(56)
            })

            addView(FooterLayout(context).apply {
                id = R.id.idFooterLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))


            addView(FrameLayout(context).apply {

                addView(ZikrButton(context).apply {
                    id = R.id.idInfoView
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

        zikrViewModel.zikrStateData.let {
            it.value?.let { state ->
                bindState(state)
            }
            it.observe(this, Observer { state ->
                bindState(state)
            })
        }

        buttonView?.setOnClickListener {
            zikrViewModel.zikrStateData.value?.let {
                when (it.reaction) {
                    0 -> {
                        makeSound()
                    }
                    1 -> {
                        vibrate()
                    }
                }
            }

            zikrViewModel.increment()
        }

    }

    private fun bindState(state: ZikrState) {
        stateView?.let {
            it.currentZikrImage = when (state.type) {
                0 -> R.drawable.img_subhan_allah
                1 -> R.drawable.img_alhamdulillah
                else -> R.drawable.img_allohu_akbar
            }
            it.progressInfo = "${state.typeProgress}/${state.typeMax}"
            it.zikrProgress = state.typeProgress.toFloat() / state.typeMax
            it.currentZikrText = "Barchasi: " + state.progress
        }

        headerLayout?.let {
            if (state.max == 99) {
                it.setSecondActionIcon(R.drawable.ic_33)
            } else {
                it.setSecondActionIcon(R.drawable.ic_99)
            }

            when (state.reaction) {
                0 -> it.setFirstActionIcon(R.drawable.ic_notification_adhan)
                1 -> it.setFirstActionIcon(R.drawable.ic_notification_silent)
                else -> it.setFirstActionIcon(R.drawable.ic_notification_off)
            }
        }

        buttonView?.count = state.typeProgress

    }

    private fun makeSound() {
        IslomUzApp.getInstance().toneManager.makeZikrTone()
    }

    private fun vibrate() {
        zing()
    }
}