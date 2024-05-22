/*
package com.example.whenandwhere

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(
    private val context: Context,
    private val numRows: Int,
    private val numColumns: Int
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private val selectedCells = mutableSetOf<Pair<Int, Int>>()

    private val defaultBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.box_background)!!
    private val selectedBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.box_selected_background)!!

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cell: LinearLayout = view.findViewById(R.id.cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position / numColumns
        val column = position % numColumns

        val isCellSelected = selectedCells.contains(Pair(row, column))

        holder.cell.background = if (isCellSelected) {
            selectedBackground
        } else {
            defaultBackground
        }

        holder.cell.setOnClickListener {
            if (isCellSelected) {
                selectedCells.remove(Pair(row, column)) // 이미 선택된 경우 해제
            } else {
                selectedCells.add(Pair(row, column)) // 선택되지 않은 경우 선택
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = numRows * numColumns // 전체 셀 수 반환
}
*/
