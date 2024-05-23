package com.example.whenandwhere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyFragment : Fragment() {

    private lateinit var WEEK: String
    private lateinit var DATA: String
    private lateinit var SCHEDULES: ArrayList<scheduleClass>

    companion object {
        fun newInstance(week : String, data: String, scheduleList : ArrayList<scheduleClass>): MyFragment{
            val args = Bundle()
            args.putString("WEEK", week)
            args.putString("DATA", data)
            args.putSerializable("SCHEDULES", scheduleList)
            val fragment = MyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            WEEK = it.getString("WEEK")!!
            DATA = it.getString("DATA")!!
            SCHEDULES = it.getSerializable("SCHEDULES") as? ArrayList<scheduleClass> ?: ArrayList()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new, container, false)

        highlightSchedule(view)

        return view
    }

    private fun highlightSchedule(view: View) {
        val dayLayouts = listOf(
            view.findViewById<LinearLayout>(R.id.box_mon),
            view.findViewById<LinearLayout>(R.id.box_tue),
            view.findViewById<LinearLayout>(R.id.box_wed),
            view.findViewById<LinearLayout>(R.id.box_thu),
            view.findViewById<LinearLayout>(R.id.box_fri),
            view.findViewById<LinearLayout>(R.id.box_sat),
            view.findViewById<LinearLayout>(R.id.box_sun)
        )

        for (schedule in SCHEDULES) {
            val startHour = schedule.startTime.substring(11, 13).toInt()
            val endHour = schedule.endTime.substring(11, 13).toInt()
            val dayIndex = getDayIndex(schedule.startTime.substring(0, 10)) // Assuming date format "yyyy-MM-dd"

            if (dayIndex in dayLayouts.indices) {
                val dayLayout = dayLayouts[dayIndex]
                for (hour in startHour..endHour) {
                    if (hour - 10 in 0 until dayLayout.childCount) {
                        val hourView = dayLayout.getChildAt(hour - 10)
                        hourView.isClickable = true // 클릭 가능 상태로 설정
                        if (isOrangeBackground(hourView)) {
                            hourView.setOnClickListener {
                                val orangeCount = countConnectedOrangeViews(hourView)
                                showCheckPopup(schedule, orangeCount)
                            }
                        }
                        hourView.setBackgroundResource(R.drawable.box_background)
                    }
                }
            }
        }
    }

    private fun countConnectedOrangeViews(clickedView: View): Int {
        val parentLayout = clickedView.parent as LinearLayout
        val clickedIndex = parentLayout.indexOfChild(clickedView)

        // 위쪽 주황색 뷰 개수 계산
        var orangeCountUp = 0
        for (i in clickedIndex - 1 downTo 0) {
            val view = parentLayout.getChildAt(i)
            if (isOrangeBackground(view)) {
                orangeCountUp++
            } else {
                break
            }
        }
        // 아래쪽 주황색 뷰 개수 계산
        var orangeCountDown = 0
        for (i in clickedIndex + 1 until parentLayout.childCount) {
            val view = parentLayout.getChildAt(i)
            if (isOrangeBackground(view)) {
                orangeCountDown++
            } else {
                break
            }
        }

        // 총 연결된 주황색 뷰 개수 반환
        return orangeCountUp + orangeCountDown + 1 // 클릭된 뷰 포함
    }

    private fun isOrangeBackground(view: View): Boolean {
        return view.background.constantState == resources.getDrawable(R.drawable.box_selected_background).constantState
    }

    private fun showCheckPopup(schedule: scheduleClass, orangeCount: Int) {
        val activity = activity ?: return
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.time_result_check_popup, null)
        val alertDialogBuilder = AlertDialog.Builder(activity).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        val TimeTextView = dialogView.findViewById<TextView>(R.id.checktext)
        TimeTextView.text = "선택된 시작 일정 : ${schedule.startTime} 종료 일정:  ${schedule.endTime}\n연결된 주황색 박스 개수 : $orangeCount"

        dialogView.findViewById<Button>(R.id.confirm).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.cancel).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun getDayIndex(dateString: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(dateString)
        val calendar = Calendar.getInstance().apply { time = date }
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> -1
        }
    }
}