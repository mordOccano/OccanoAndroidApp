package com.e.occanotestsidep.ui.main.dashboard


import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.e.occanotestsidep.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 */
class SubDadhboardContainer : Fragment() {

    private lateinit var sabaDushCollectionAdapter: SabaDushCollectionAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_dadhboard_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sabaDushCollectionAdapter = SabaDushCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = sabaDushCollectionAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                when(position){
                    0 -> {
                        tab.text = "Calibration"
                        tab.icon = R.drawable.ic_report_black_24dp.toDrawable()
                    }
                    1-> {
                        tab.text = "Cylinders"
                        tab.icon = R.drawable.ic_filter_center_focus_black_24dp.toDrawable()
                    }
                    2-> {
                        tab.text = "Graphs"
                        tab.icon = R.drawable.ic_trending_up_black_24dp.toDrawable()
                    }
                }
            }).attach()
    }


    }





