package highland.lights.highlandlights


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import highland.lights.protocol.LightConfig
import highland.lights.protocol.LightInterface
import highland.lights.protocol.LightRange
import highland.lights.protocol.LightStrip
import java.lang.NullPointerException
import java.lang.NumberFormatException
import java.net.InetAddress


class LightConfigActivity : AppCompatActivity() {
    var stripViews: ArrayList<RecyclerView> = arrayListOf<RecyclerView>();
    var config : Array<LightStrip?> = arrayOfNulls<LightStrip>(4);
    lateinit var devIp : InetAddress;
    var devPort : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_config)

        // Setup all the variables we need
        stripViews.add(findViewById<RecyclerView>(R.id.Strip1))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip2))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip3))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip4))
        devIp = (intent.getSerializableExtra("devIP")) as InetAddress
        devPort = intent.getIntExtra("devPort", 0)

        val button_as1 = findViewById<Button>(R.id.add_strip_1)
        button_as1?.setOnClickListener()
        {
            if(config[0] == null) {
                val stripView = findViewById<RecyclerView>(R.id.Strip1)
                stripView.background.setTint(Color.parseColor("#58ff4f"))
                val ls: LightStrip = LightStrip(0, ArrayList<LightRange>())
                config[0] = ls
                stripView.adapter = LightRangeAdapter(applicationContext, ls.ranges)
                stripView.layoutManager = LinearLayoutManager(applicationContext)
                stripView.itemAnimator = DefaultItemAnimator()
            }
            else
            {
                config[0] = null
                val stripView = findViewById<RecyclerView>(R.id.Strip1)
                stripView.background.setTint(Color.parseColor("#FB7676"))
            }
        }
        val button_as2 = findViewById<Button>(R.id.add_strip_2)
        button_as2?.setOnClickListener()
        {
            if(config[1] == null) {
                val stripView = findViewById<RecyclerView>(R.id.Strip2)
                stripView.background.setTint(Color.parseColor("#58ff4f"))
                val ls: LightStrip = LightStrip(0, ArrayList<LightRange>())
                config[1] = ls
                stripView.adapter = LightRangeAdapter(applicationContext, ls.ranges)
                stripView.layoutManager = LinearLayoutManager(applicationContext)
                stripView.itemAnimator = DefaultItemAnimator()
            }
            else
            {
                config[1] = null
                val stripView = findViewById<RecyclerView>(R.id.Strip2)
                stripView.background.setTint(Color.parseColor("#FB7676"))
            }
        }
        val button_as3 = findViewById<Button>(R.id.add_strip_3)
        button_as3?.setOnClickListener()
        {
            if(config[2] == null) {
                val stripView = findViewById<RecyclerView>(R.id.Strip3)
                stripView.background.setTint(Color.parseColor("#58ff4f"))
                val ls: LightStrip = LightStrip(0, ArrayList<LightRange>())
                config[2] = ls
                stripView.adapter = LightRangeAdapter(applicationContext, ls.ranges)
                stripView.layoutManager = LinearLayoutManager(applicationContext)
                stripView.itemAnimator = DefaultItemAnimator()
            }
            else
            {
                config[2] = null
                val stripView = findViewById<RecyclerView>(R.id.Strip3)
                stripView.background.setTint(Color.parseColor("#FB7676"))
            }
        }
        val button_as4 = findViewById<Button>(R.id.add_strip_4)
        button_as4?.setOnClickListener()
        {
            if(config[3] == null) {
                val stripView = findViewById<RecyclerView>(R.id.Strip4)
                stripView.background.setTint(Color.parseColor("#58ff4f"))
                val ls: LightStrip = LightStrip(0, ArrayList<LightRange>())
                config[3] = ls
                stripView.adapter = LightRangeAdapter(applicationContext, ls.ranges)
                stripView.layoutManager = LinearLayoutManager(applicationContext)
                stripView.itemAnimator = DefaultItemAnimator()
            }
            else
            {
                config[3] = null
                val stripView = findViewById<RecyclerView>(R.id.Strip4)
                stripView.background.setTint(Color.parseColor("#FB7676"))
            }
        }

        val button_ar1 = findViewById<Button>(R.id.add_range_1)
        button_ar1?.setOnClickListener()
        {
            val strip = config[0]
            if(strip != null)
            {
                getRangeFromDialog(strip, 1)
                stripViews[0].adapter?.notifyDataSetChanged()
            }
        }
        val button_ar2 = findViewById<Button>(R.id.add_range_2)
        button_ar2?.setOnClickListener()
        {
            val strip = config[1]
            if (strip != null)
            {
                getRangeFromDialog(strip, 2)
                stripViews[1].adapter?.notifyDataSetChanged()
            }
        }
        val button_ar3 = findViewById<Button>(R.id.add_range_3)
        button_ar3?.setOnClickListener()
        {
            val strip = config[2]
            if(strip != null)
            {
                getRangeFromDialog(strip, 3)
                stripViews[2].adapter?.notifyDataSetChanged()
            }
        }
        val button_ar4 = findViewById<Button>(R.id.add_range_4)
        button_ar4?.setOnClickListener()
        {
            val strip = config[3]
            if (strip != null)
            {
                getRangeFromDialog(strip, 4)
                stripViews[3].adapter?.notifyDataSetChanged()
            }
        }

        val submit_button = findViewById<Button>(R.id.submit_light_config)
        submit_button?.setOnClickListener()
        {
            val lightInterface: LightInterface = LightInterface()
            Log.d("CONN", devIp.toString());
            Log.d("CONN", devPort.toString());
            lightInterface.sendLightConfig(devIp, devPort, LightConfig(config))
            var intent = Intent(this, LightControlActivity::class.java)
            // @todo(apz) Lets make the control activity more clever, i.e. let it discover all
            //            available strips. This requires a little more embedded work
            intent.putExtra("devIP", devIp)
            intent.putExtra("devPort", devPort)
            // @todo(apz) please fix fucking garbage (clearly we want config to be serializable but
            //            I am clearly too dim to do so)
            intent.putExtra("config", (config.map { it?.num_leds }).toTypedArray())

            startActivity(intent)
        }
    }

    fun getRangeFromDialog(strip : LightStrip, idx : Int)
    {
        val txtEnd = EditText(this)
        txtEnd.inputType = TYPE_CLASS_NUMBER
        val minEnd = strip.num_leds

        AlertDialog.Builder(this)
                .setTitle("New Range End")
                .setMessage("This range will start from: $minEnd")
                .setView(txtEnd)
                .setPositiveButton("New", DialogInterface.OnClickListener { dialog, whichButton ->
                    try {
                        val rangeEnd = txtEnd.text.toString().toShort()
                        if(rangeEnd > minEnd) {
                            strip.ranges.add(LightRange(idx.toChar(), minEnd, rangeEnd))
                            strip.num_leds = rangeEnd
                        }
                        else
                        {
                            val text = "Range end needs to be after the current range"
                            val duration = Toast.LENGTH_LONG

                            val toast = Toast.makeText(applicationContext, text, duration)
                            toast.show()
                        }
                    }
                    catch(e : NumberFormatException)
                    {
                        val text = "Range end needs to be after the current range"
                        val duration = Toast.LENGTH_LONG

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }
                    catch (e : NullPointerException)
                    {
                        val text = "Range end needs to be after the current range"
                        val duration = Toast.LENGTH_LONG

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }

                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton -> })
                .show()
    }

}