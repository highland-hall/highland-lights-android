package highland.lights.highlandlights


import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import highland.lights.protocol.LightRange
import highland.lights.protocol.LightStrip


class LightConfigActivity : AppCompatActivity() {
    var stripViews: ArrayList<RecyclerView> = arrayListOf<RecyclerView>();
    var config : Array<LightStrip?> = arrayOfNulls<LightStrip>(4);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_config)

        // Setup all the variables we need

        stripViews.add(findViewById<RecyclerView>(R.id.Strip1))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip2))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip3))
        stripViews.add(findViewById<RecyclerView>(R.id.Strip4))


        val button_as1 = findViewById<Button>(R.id.add_strip_1)
        button_as1?.setOnClickListener()
        {
            val stripView = findViewById<RecyclerView>(R.id.Strip1)
            stripView.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[0] = ls
            stripView.adapter = LightRangeAdapter(applicationContext,ls.ranges)
            stripView.layoutManager = LinearLayoutManager(applicationContext)
            stripView.itemAnimator = DefaultItemAnimator()
        }
        val button_as2 = findViewById<Button>(R.id.add_strip_2)
        button_as2?.setOnClickListener()
        {
            val stripView = findViewById<RecyclerView>(R.id.Strip2)
            stripView.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[1] = ls
            stripView.adapter = LightRangeAdapter(applicationContext,ls.ranges)
            stripView.layoutManager = LinearLayoutManager(applicationContext)
            stripView.itemAnimator = DefaultItemAnimator()
        }
        val button_as3 = findViewById<Button>(R.id.add_strip_3)
        button_as3?.setOnClickListener()
        {
            val stripView = findViewById<RecyclerView>(R.id.Strip3)
            stripView.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[2] = ls
            stripView.adapter = LightRangeAdapter(applicationContext,ls.ranges)
            stripView.layoutManager = LinearLayoutManager(applicationContext)
            stripView.itemAnimator = DefaultItemAnimator()
        }
        val button_as4 = findViewById<Button>(R.id.add_strip_4)
        button_as4?.setOnClickListener()
        {
            val stripView = findViewById<RecyclerView>(R.id.Strip4)
            stripView.background.setTint(Color.parseColor("#58ff4f"))
            val ls : LightStrip = LightStrip(0, ArrayList<LightRange>())
            config[3] = ls
            stripView.adapter = LightRangeAdapter(applicationContext,ls.ranges)
            stripView.layoutManager = LinearLayoutManager(applicationContext)
            stripView.itemAnimator = DefaultItemAnimator()
        }

        val button_ar1 = findViewById<Button>(R.id.add_range_1)
        button_ar1?.setOnClickListener()
        {
            val intent = Intent(this, NewRangeActivity::class.java)
            startActivityForResult(intent, 1);
        }
        val button_ar2 = findViewById<Button>(R.id.add_range_2)
        button_ar2?.setOnClickListener()
        {
            val intent = Intent(this, NewRangeActivity::class.java)
            startActivityForResult(intent, 2);
        }
        val button_ar3 = findViewById<Button>(R.id.add_range_3)
        button_ar3?.setOnClickListener()
        {
            val intent = Intent(this, NewRangeActivity::class.java)
            startActivityForResult(intent, 3);
        }
        val button_ar4 = findViewById<Button>(R.id.add_range_4)
        button_ar4?.setOnClickListener()
        {
            val intent = Intent(this, NewRangeActivity::class.java)
            startActivityForResult(intent, 4);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            val stripIndex: Int? = data?.getIntExtra("strip_index",0)
            val startIndex: Int? = data?.getIntExtra("start_index",0)
            val endIndex: Int? = data?.getIntExtra("end_index",0)

            if(stripIndex == null || startIndex == null || endIndex == null)
            {
                val text = "Range Creation Failed, bad config"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                return
            }
            // Get the relevant strip view and config
            val stripView = stripViews[requestCode-1]
            val ls = config[requestCode-1]
            if(ls == null)
            {
                val text = "Range Creation Failed, LS null"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                return
            }
            ls.ranges.add(LightRange(stripIndex.toChar(),startIndex.toShort(), endIndex.toShort()))
            stripView.adapter?.notifyDataSetChanged()

            Log.d("REQUEST", requestCode.toString())
            Log.d("VIEWCOUNT", stripView.adapter?.itemCount.toString())
        }
        else
        {
            val text = "Cancelled"
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            return
        }
    }
}