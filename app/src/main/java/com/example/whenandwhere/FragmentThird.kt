package com.example.whenandwhere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager

class FragmentThird : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        val itemDecoration = TimeTableItemDecoration(requireContext())
        recyclerView.addItemDecoration(itemDecoration)

        val gridLayoutManager = GridLayoutManager(requireContext(), 7)
        recyclerView.layoutManager = gridLayoutManager

        // scheduleClass 객체 리스트 생성
        val scheduleList = mutableListOf<scheduleClass>()
        for (i in 1..12) { // 임의로 12개의 시간 슬롯 생성
            for (j in 1..7) { // 7개의 요일 생성
                scheduleList.add(scheduleClass(i, "Schedule $i-$j"))
            }
        }

        val adapter = ScheduleAdapter(requireContext(), scheduleList)
        recyclerView.adapter = adapter
    }
}