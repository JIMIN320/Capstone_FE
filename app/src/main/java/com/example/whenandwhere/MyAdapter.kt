package com.example.whenandwhere

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(private val fa: FragmentActivity, private val count: Int) : FragmentStateAdapter(fa) {

    // 각 프래그먼트를 저장할 리스트
    private val fragments = ArrayList<Fragment>()

    // 각 프래그먼트에 대한 참조를 가져오기 위해 프래그먼트를 생성할 때 리스트에 추가합니다.
    init {
        for (i in 0 until count) {
            fragments.add(when (i) {
                0 -> FragmentFirst()
                1 -> FragmentSecond()
                2 -> FragmentThird()
                else -> FragmentFourth()
            })
        }
    }

    override fun createFragment(position: Int): Fragment {
        // 무한 슬라이딩을 위해 나머지 연산을 사용하여 프래그먼트를 가져옵니다.
        return fragments[position % count]
    }

    override fun getItemCount(): Int {
        // 여기서는 충분히 큰 값으로 설정하여 무한 슬라이딩이 가능하도록 합니다.
        return Int.MAX_VALUE
    }

    // 각 프래그먼트에 대한 참조를 반환하는 메서드
    fun getFragment(position: Int): Fragment {
        return fragments[position % count]
    }
}
