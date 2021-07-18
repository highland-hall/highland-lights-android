package highland.lights.highlandlights

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import highland.lights.protocol.LightInterface
import highland.lights.protocol.LightRange
import java.lang.NullPointerException
import java.lang.NumberFormatException
import java.net.InetAddress


class MainActivity : AppCompatActivity() {

    val TAG = "LightConfig"
    lateinit var nsdManager : NsdManager
    val SERVICE_TYPE = "_highlandlights._tcp"
    var availableDevices : ArrayList<Pair<InetAddress, Int>> = arrayListOf<Pair<InetAddress, Int>>()

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success$service")
            nsdManager.resolveService(service, resolveListener)
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }
    }
    private val resolveListener = object : NsdManager.ResolveListener {

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")
            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host
            availableDevices.add(Pair(host, port))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nsdManager = applicationContext.getSystemService(Context.NSD_SERVICE) as NsdManager
        //Search for configs
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)

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
            //startActivity(intent)
        }
        val setConnButton = findViewById<Button>(R.id.setConnectionButton)
        setConnButton?.setOnClickListener()
        {
            // setup the alert builder
            Log.d(TAG, "Length of available dev list: ${availableDevices.size}")
            android.app.AlertDialog.Builder(this)
                    .setTitle("Choose a device to connect to")
                    .setItems((availableDevices.map {(inet, port) -> inet.toString()}).toTypedArray(),DialogInterface.OnClickListener
                    {
                        dialog, whichButton ->
                        val intent = Intent(this, LightConfigActivity::class.java)
                        intent.putExtra("devIP", availableDevices[whichButton].first)
                        intent.putExtra("devPort", availableDevices[whichButton].second)
                        startActivity(intent)
                    })
                    .create()
                    .show()
        }
    }

}