import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomama.ItemTukar
import com.example.ecomama.R
import com.example.ecomama.TukarPointActivity

class ItemAdapter(
    private val context: TukarPointActivity,
    private val itemList: List<ItemTukar>,
    private val itemClickListener: (ItemTukar) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val itemPoints: TextView = itemView.findViewById(R.id.itemPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        holder.itemPoints.text = "${item.points} poin"
        Glide.with(context).load(item.image).into(holder.itemImage) // Load gambar dengan Glide

        holder.itemView.setOnClickListener {
            itemClickListener(item) // Handle klik item
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
