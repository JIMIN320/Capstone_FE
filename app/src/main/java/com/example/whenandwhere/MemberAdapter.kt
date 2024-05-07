package com.example.whenandwhere
/*
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val groupNameTextView: TextView = itemView.findViewById(R.id.groupname)
    private val groupThemeTextView: TextView = itemView.findViewById(R.id.grouptheme)

    fun bind(member: String) {
        groupNameTextView.text = member
        groupThemeTextView.text = ""
    }
}
class MemberAdapter(private val context: Context, private val memberList: ArrayList<String>) :
    RecyclerView.Adapter<MemberAdapter.CustomViewHolder>() {
    // 클릭 이벤트 리스너 정의
    interface OnItemClickListener {
        fun onItemClick(member: String)
    }

    // 리스너 변수 선언
    private var listener: OnItemClickListener? = null

    // 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val members = memberList[position]
        holder.bind(members)

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScheduleSetting::class.java).apply{
                putExtra("nickname",members[position])
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return memberList.size
    }



    fun addMember(member: String) {
        memberList.add(member)
        notifyItemInserted(memberList.size - 1)
    }
}
*/
