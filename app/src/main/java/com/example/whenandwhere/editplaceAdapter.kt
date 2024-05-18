import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whenandwhere.R
import com.example.whenandwhere.editplaceClass

class editplaceAdapter(
    private val items: List<editplaceClass>,
    private val onInputButtonClickListener: (editplaceClass) -> Unit,
    private val onIconToggleClickListener: (editplaceClass) -> Unit
) : RecyclerView.Adapter<editplaceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView = view.findViewById(R.id.username)
        val inputButton: Button = view.findViewById(R.id.inputbtn)
        val iconButton: ImageButton = view.findViewById(R.id.imageView_trans1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.userNameTextView.text = item.userName
        holder.inputButton.text = item.departurePlace
        holder.iconButton.setImageResource(
            if (item.isCarIcon) R.drawable.car_orange else R.drawable.bus_orange)

        holder.inputButton.setOnClickListener {
            onInputButtonClickListener(item)
        }

        holder.iconButton.setOnClickListener {
            item.isCarIcon = !item.isCarIcon
            notifyItemChanged(position)
            onIconToggleClickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
