package highland.lights.highlandlights

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import highland.lights.protocol.LightInterface
import java.net.InetAddress
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.sendConfigButton)
        button?.setOnClickListener()
        {
            val light_ip : InetAddress = InetAddress.getByName(findViewById<EditText>(R.id.lightConfigIp).text.toString())
            val light_port : Int = Integer.parseInt(findViewById<EditText>(R.id.lightConfigPort).text.toString())
            val config_ssid : String = findViewById<EditText>(R.id.configSSID).text.toString()
            val config_pass : String = findViewById<EditText>(R.id.configPass).text.toString()

            val light_interface : LightInterface = LightInterface()
            Log.d("SSID", config_ssid)
            Log.d("PASS", config_pass)
            light_interface.sendWifiConfig(light_ip, light_port, config_ssid, config_pass)
            val intent = Intent(this, LightConfigActivity::class.java)
            startActivity(intent)
        }
    }

}