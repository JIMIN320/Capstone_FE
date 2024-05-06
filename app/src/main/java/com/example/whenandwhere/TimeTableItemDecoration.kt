package com.example.whenandwhere

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TimeTableItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val solidLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.black) // 실선 색상
        strokeWidth = 4f // 실선 두께
    }

    private val dottedLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.black) // 점선 색상
        strokeWidth = 2f // 점선 두께
        pathEffect = android.graphics.DashPathEffect(floatArrayOf(10f, 10f), 0f) // 점선 효과
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val parentWidth = parent.width

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + 1 // 구분선 높이 설정

            if (i % 2 == 0) { // 각 시간 단위마다 실선
                canvas.drawLine(0f, top.toFloat(), parentWidth.toFloat(), top.toFloat(), solidLinePaint)
            } else { // 30분 단위마다 점선
                canvas.drawLine(0f, top.toFloat(), parentWidth.toFloat(), top.toFloat(), dottedLinePaint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, 1) // 구분선 공간 설정
    }
}
