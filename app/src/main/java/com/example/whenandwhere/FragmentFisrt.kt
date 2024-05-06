package com.example.whenandwhere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FragmentFirst : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        // RecyclerView에 추가할 ItemDecoration 객체를 생성합니다.
        val itemDecoration = TimeTableItemDecoration(requireContext())
        recyclerView.addItemDecoration(itemDecoration)

        // 여기서 RecyclerView에 어댑터를 설정하거나 기타 초기화를 수행할 수 있습니다.
    }
}