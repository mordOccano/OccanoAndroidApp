package com.e.occanotestsidep.ui.main.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SabaDushCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3
    //        {}
    override fun createFragment(position: Int): Fragment {

            when(position){
                0 -> return CalibrationFragment()
                1 -> return CylindersFragment()
                2 -> return GraphsFragment()

                else -> return CalibrationFragment()

            }

//        val fragment =  DemoObjectFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(ARG_OBJECT, position+1 )
//        }
//        return fragment
    }
}