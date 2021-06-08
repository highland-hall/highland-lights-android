package highland.lights.highlandlights

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import highland.lights.protocol.LightRange

class LightRangeAdapter(private val context: Context, private val range_list : ArrayList<LightRange>)
    : RecyclerView.Adapter<LightRangeAdapter.LightRangeViewHolder>() {
    class LightRangeViewHolder(private val view: View) : RecyclerView.ViewHolder(view)  {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightRangeViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.range_item, parent, false)

        return LightRangeAdapter.LightRangeViewHolder(adapterLayout)


    }

    override fun onBindViewHolder(holder: LightRangeViewHolder, position: Int) {
        holder.textView.text = range_list[position].strip_idx.toString()

    }

    override fun getItemCount(): Int {
        return range_list.size
    }
}