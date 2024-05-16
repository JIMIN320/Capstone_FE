package com.example.whenandwhere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class acceptAdapter (private val itemList: List<acceptClass>, private val listener: acceptAdapter.ButtonClickListener) :
    RecyclerView.Adapter<acceptAdapter.ViewHolder>() {

        interface ButtonClickListener {
            fun onButtonClick(position: Int)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.name)
            val button: Button = itemView.findViewById(R.id.accept)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.accept_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = itemList[position]
            holder.textView.text = currentItem.name

            holder.button.setOnClickListener {
                listener.onButtonClick(position)
            }
        }

        override fun getItemCount() = itemList.size
}