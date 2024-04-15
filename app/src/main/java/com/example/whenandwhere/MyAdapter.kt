package com.example.whenandwhere

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(private val fa: FragmentActivity, private val count: Int) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when (position % count) {
            0 -> FragmentFirst()
            1 -> FragmentSecond()
            2 -> FragmentThird()
            else -> FragmentFourth()
        }
    }

    override fun getItemCount(): Int {
        // 여기서는 충분히 큰 값으로 설정하여 무한 슬라이딩이 가능하도록 합니다.
        return Int.MAX_VALUE
    }
}