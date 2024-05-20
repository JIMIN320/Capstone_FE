package com.example.whenandwhere

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(val context : Context, private val scheduleList: List<scheduleClass>) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.bind(schedule)

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ScheduleSetting::class.java).apply{
                putExtra("memberId", schedule.id)
                putExtra("memberNickname", schedule.name)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name)

        fun bind(schedule: scheduleClass) {
            nameTextView.text = schedule.name
        }
    }
}