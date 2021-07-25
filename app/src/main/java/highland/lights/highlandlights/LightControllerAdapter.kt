package highland.lights.highlandlights

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.larswerkman.holocolorpicker.*
import highland.lights.protocol.LightInterface
import highland.lights.protocol.LightRange


class LightControllerAdapter(
    private val context: Context,
    private val strip_list: ArrayList<StripController>
) : RecyclerView.Adapter<LightControllerAdapter.LightControllerViewHolder>() {
    class LightControllerViewHolder(private val view: View) : RecyclerView.ViewHolder(view)  {
        init {
            val picker = view.findViewById(R.id.picker) as ColorPicker
            val saturationBar = view.findViewById(R.id.saturationbar) as SaturationBar
            val valueBar = view.findViewById(R.id.valuebar) as ValueBar
            picker.addSaturationBar(saturationBar)
            picker.addValueBar(valueBar)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightControllerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.light_controller,
            parent,
            false
        )

        return LightControllerViewHolder(adapterLayout)


    }

    override fun onBindViewHolder(holder: LightControllerViewHolder, position: Int) {
        val submitButton = holder.itemView.findViewById(R.id.submit_color_button) as Button
        val picker = holder.itemView.findViewById(R.id.picker) as ColorPicker
        submitButton.setOnClickListener()
        {
            val color = picker.color
            val iface = LightInterface()
            iface.setStripToColor(strip_list[position].addr, strip_list[position].port, strip_list[position].strip_idx, Triple(Color.red(color),Color.green(color),Color.blue(color)))
        }
        Log.d("LRADAPT", "binding")
    }

    override fun getItemCount(): Int {
        return strip_list.size
    }
}
