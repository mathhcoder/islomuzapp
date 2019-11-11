package uz.islom.ui.fragment.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.islom.R
import uz.islom.ext.string
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ui.custom.BaseTextView
import uz.islom.model.dm.Theme
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp


class MainFragment : BaseFragment() {

    private val appTheme = Theme.GREEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LinearLayout(inflater.context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM

            addView(BaseTextView(context).apply {
                id = R.id.idTextView
                gravity = Gravity.CENTER
                maxLines = 1
                text = string(R.string.app_name)
                setTextSizeSp(20)
            }, LinearLayout.LayoutParams(full, dp(56)))

            addView(ViewPager(context).apply {
                id = R.id.idViewPager
            }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f))

            addView(BottomNavigationView(context, null, R.style.BottomNavigationViewTheme).apply {

                id = R.id.bottomNavigationView
                menu.add(0, 0, 1, string(R.string.home)).setIcon(R.drawable.ic_menu)
                menu.add(0, 1, 1, string(R.string.web)).setIcon(R.drawable.ic_web)
                menu.add(0, 2, 1, string(R.string.settings)).setIcon(R.drawable.ic_settings)

                val colors = intArrayOf(appTheme.navigationIconsColor,appTheme.primaryColor)
                val states = arrayOf(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked), intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked))

                itemTextColor = ColorStateList(states, colors)
                itemIconTintList = ColorStateList(states, colors)

            }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager>(R.id.idViewPager)?.apply {

            offscreenPageLimit = 5

            adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

                override fun getItem(position: Int): BaseFragment {
                    return when (position) {
                        0 -> FunctionsFragment.newInstance()
                        1 -> SitesFragment.newInstance()
                        else -> ProfileFragment.newInstance()
                    }
                }

                override fun getCount(): Int = 3
            }
        }

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.apply {

            setOnNavigationItemSelectedListener {
                viewPager?.currentItem = it.itemId
                true
            }
        }

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bottomNavigationView?.menu?.getItem(position)?.isChecked = true
            }

        })

        bindTheme(Theme.GREEN)

    }

    private fun bindTheme(appTheme: Theme) {
        view?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.apply {
            setBackgroundColor(appTheme.secondaryColor)
        }

        view?.findViewById<TextView>(R.id.idTextView)?.apply {
            setTextColor(appTheme.secondaryColor)
            setBackgroundColor(appTheme.toolBarColor)
        }
    }
}