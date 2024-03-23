package com.example.whenandwhere

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(val Courselist: ArrayList<Courses>) :
    RecyclerView.Adapter<CourseAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Courselist.size
    }

    override fun onBindViewHolder(holder: CourseAdapter.CustomViewHolder, position: Int) {
        holder.name1.text = Courselist.get(position).name1
        holder.distance1.text = Courselist.get(position).distance1
        holder.address1.text = Courselist.get(position).address1
        holder.name2.text = Courselist.get(position).name2
        holder.distance2.text = Courselist.get(position).distance2
        holder.address2.text = Courselist.get(position).address2
        holder.name3.text = Courselist.get(position).name3
        holder.distance3.text = Courselist.get(position).distance3
        holder.address3.text = Courselist.get(position).address3
    }

    class CustomViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val name1 = itemview.findViewById<TextView>(R.id.name1)
        val distance1 = itemview.findViewById<TextView>(R.id.distance1)
        val address1 = itemview.findViewById<TextView>(R.id.address1)
        val name2 = itemview.findViewById<TextView>(R.id.name2)
        val distance2 = itemview.findViewById<TextView>(R.id.distance2)
        val address2 = itemview.findViewById<TextView>(R.id.address2)
        val name3 = itemview.findViewById<TextView>(R.id.name3)
        val distance3 = itemview.findViewById<TextView>(R.id.distance3)
        val address3 = itemview.findViewById<TextView>(R.id.address3)

    }
}