package highland.lights.highlandlights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import highland.lights.protocol.LightStrip
import java.net.InetAddress

class LightControlActivity : AppCompatActivity() {
    var controllerList : ArrayList<StripController> = arrayListOf<StripController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_control)
        val stripControllers = findViewById<RecyclerView>(R.id.StripControllers)

        val devIp = (intent.getSerializableExtra("devIP")) as InetAddress
        val devPort = intent.getIntExtra("devPort", 0)

        val strips = (intent.getSerializableExtra("config")) as Array<Short?>

        for((idx,strip) in strips.withIndex())
        {
            if(strip == null)
            {
                continue
            }
            controllerList.add(StripController(devIp, devPort, idx))
        }

        stripControllers.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)
        stripControllers.adapter = LightControllerAdapter(applicationContext, controllerList)
        stripControllers.itemAnimator = DefaultItemAnimator()
    }
}