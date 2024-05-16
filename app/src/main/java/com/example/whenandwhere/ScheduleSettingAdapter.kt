package com.example.whenandwhere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleSettingAdapter(private val scheduleList: List<ScheduleItem>) : RecyclerView.Adapter<ScheduleSettingAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentItem = scheduleList[position]
        holder.textView.text = currentItem.scheduleText
    }

    override fun getItemCount() = scheduleList.size

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.schedule_text)
    }
}
