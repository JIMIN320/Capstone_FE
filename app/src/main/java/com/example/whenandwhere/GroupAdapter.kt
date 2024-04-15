package com.example.whenandwhere

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(private val context: Context, private val groupList: ArrayList<Groups>) :
    RecyclerView.Adapter<GroupAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val group = groupList[position]
        holder.bind(group)

    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val groupNameTextView: TextView = itemView.findViewById(R.id.groupname)
        private val groupThemeTextView: TextView = itemView.findViewById(R.id.grouptheme)

        fun bind(group: Groups) {
            groupNameTextView.text = group.Groupname
            groupThemeTextView.text = group.Grouptheme
        }
    }

    fun addGroup(group: Groups) {
        groupList.add(group)
        notifyItemInserted(groupList.size - 1)
    }
}
