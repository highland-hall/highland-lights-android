package highland.lights.highlandlights

import android.graphics.Color
import highland.lights.protocol.LightInterface
import java.net.InetAddress

class StripController(val addr : InetAddress, val port : Int, val strip_idx : Int) {

    fun setStripToColor(color: Color)
    {
        val iface = LightInterface()
    }
}