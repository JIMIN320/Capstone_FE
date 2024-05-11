package com.example.whenandwhere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager

class FragmentFourth : Fragment() {

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

        val adapter = ScheduleAdapter(requireContext(), 12, 7) // 12시간, 7요일
        recyclerView.adapter = adapter
    }
}