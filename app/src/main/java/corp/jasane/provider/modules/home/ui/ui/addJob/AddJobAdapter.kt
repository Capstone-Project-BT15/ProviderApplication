package corp.jasane.provider.modules.home.ui.ui.addJob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import corp.jasane.provider.R

class AddJobAdapter(
    private var mList: ArrayList<String>,
    private val itemClickListener: (String) -> Unit):
    RecyclerView.Adapter<AddJobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_bottom_sheet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.item.text = item

        holder.itemView.setOnClickListener {
            // Trigger the itemClickListener when an item is clicked
            itemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){

        val item: TextView = ItemView.findViewById(R.id.selected_item)

    }
}