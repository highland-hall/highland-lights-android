package highland.lights.highlandlights

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView


import highland.lights.protocol.LightConfig
import highland.lights.protocol.LightInterface
import highland.lights.protocol.LightRange
import highland.lights.protocol.LightStrip
import java.net.InetAddress

class LightConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_config)
        val config : Array<LightStrip?> = arrayOfNulls<LightStrip>(4);

        val button_as1 = findViewById<Button>(R.id.add_strip_1)
        button_as1?.setOnClickListener()
        {
            val strip = findViewById<RecyclerView>(R.id.Strip1)
            strip.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[0] = ls
        }
        val button_as2 = findViewById<Button>(R.id.add_strip_2)
        button_as2?.setOnClickListener()
        {
            val strip = findViewById<RecyclerView>(R.id.Strip2)
            strip.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[1] = ls
        }
        val button_as3 = findViewById<Button>(R.id.add_strip_3)
        button_as3?.setOnClickListener()
        {
            val strip = findViewById<RecyclerView>(R.id.Strip3)
            strip.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[2] = ls
        }
        val button_as4 = findViewById<Button>(R.id.add_strip_4)
        button_as4?.setOnClickListener()
        {
            val strip = findViewById<RecyclerView>(R.id.Strip4)
            strip.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[3] = ls
        }

        val button_ar1 = findViewById<Button>(R.id.add_range_1)
        button_ar1?.setOnClickListener()
        {
            config[0]?.ranges?.add(LightRange(0.toChar(),0,10))
        }
        val button_ar2 = findViewById<Button>(R.id.add_range_2)
        button_ar2?.setOnClickListener()
        {
            config[1]?.ranges?.add(LightRange(0.toChar(),0,10))
        }
        val button_ar3 = findViewById<Button>(R.id.add_range_3)
        button_ar3?.setOnClickListener()
        {
            config[2]?.ranges?.add(LightRange(0.toChar(),0,10))
        }
        val button_ar4 = findViewById<Button>(R.id.add_range_4)
        button_ar4?.setOnClickListener()
        {
            config[3]?.ranges?.add(LightRange(0.toChar(),0,10))
        }
    }
}