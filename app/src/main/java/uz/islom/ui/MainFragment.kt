package uz.islom.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.islom.ui.base.BaseFragment
import uz.islom.R

class MainFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LinearLayout(inflater.context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM

            addView(ViewPager(context).apply {
                id = R.id.viewPager

            }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f))

            addView(BottomNavigationView(context).apply {
                id = R.id.bottomNavigationView
                menu.add(0, 0, 1, "Asosiy").setIcon(R.drawable.ic_dashboard)
                menu.add(0, 1, 1, "Sitelar").setIcon(R.drawable.ic_dashboard)
                menu.add(0, 2, 1, "Sozlamalar").setIcon(R.drawable.ic_account)

            }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)?.apply {
            adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getItem(position: Int): BaseFragment {
                    return when (position) {
                        0 -> FunctionsFragment.newInstance()
                        1 -> SiteListFragment.newInstance()
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
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView?.menu?.getItem(position)?.isChecked = true
            }

        })

    }
}