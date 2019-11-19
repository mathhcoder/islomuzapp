package uz.islom.ui.fragment.quran

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Theme
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ui.custom.FooterLayout
import uz.islom.ui.custom.HeaderLayout
import uz.islom.ui.fragment.BaseFragment


class QuranFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = QuranFragment()
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: Theme): View? {

        return FrameLayout(inflater.context).apply {

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                title = string(R.string.quran)
                theme = appTheme
                setUpBackAction {
                    fragmentManager?.popBackStack()
                }
                setUpFirstAction(R.drawable.ic_settings_material) {

                }
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(TabLayout(context).apply {
                id = R.id.idTabLayout
                setBackgroundColor(appTheme.toolBarColor)
                setTabTextColors(appTheme.secondaryColor.getColorWithAlpha(0.7f), appTheme.secondaryColor)
                setSelectedTabIndicatorColor(appTheme.secondaryColor)

                addTab(newTab().apply {
                    setText(R.string.surah)
                })
                addTab(newTab().apply {
                    setText(R.string.juz)
                })
                addTab(newTab().apply {
                    setText(R.string.bookmark)
                })

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        view?.findViewById<ViewPager>(R.id.idViewPager)?.currentItem = selectedTabPosition
                    }

                })

            }, FrameLayout.LayoutParams(full, dp(56)).apply {
                topMargin = dp(56)
            })

            addView(FooterLayout(context).apply {
                id = R.id.idFooterLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))

            addView(ViewPager(context).apply {
                id = R.id.idViewPager
                offscreenPageLimit = 5

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        view?.findViewById<TabLayout>(R.id.idTabLayout)?.getTabAt(position)?.select()
                    }

                })

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(112)
            })

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?,appTheme: Theme) {

        view.findViewById<ViewPager>(R.id.idViewPager)?.let {

            it.adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

                override fun getItem(position: Int): BaseFragment {
                    return when (position) {
                        0 -> SurahListFragment.newInstance()
                        1 -> SurahListFragment.newInstance()
                        else -> SurahListFragment.newInstance()
                    }
                }

                override fun getCount(): Int = 3
            }
        }
    }

}