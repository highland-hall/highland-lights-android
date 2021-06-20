package highland.lights.highlandlights

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception


class NewRangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_range)

        val button_submit = findViewById<Button>(R.id.submit_button)
        button_submit?.setOnClickListener()
        {
            try {
                val stripIndexText = findViewById<TextView>(R.id.strip_index)
                val stripIndex = stripIndexText.text.toString().toInt()
                val startIndexText = findViewById<TextView>(R.id.start_index)
                val startIndex = startIndexText.text.toString().toInt()
                val endIndexText = findViewById<TextView>(R.id.end_index)
                val endIndex = endIndexText.text.toString().toInt()

                val _result = Intent()
                _result.putExtra("strip_index", stripIndex)
                _result.putExtra("start_index", startIndex)
                _result.putExtra("end_index", endIndex)

                setResult(RESULT_OK, _result)
                finish()
            }
            catch (e : Exception)
            {
                // Hahaha just catch everything :)
                val _result = Intent()
                setResult(RESULT_CANCELED, _result)
                finish()
            }

        }

        val button_cancel = findViewById<Button>(R.id.cancel_range_button)
        button_cancel?.setOnClickListener()
        {
            val _result = Intent()
            setResult(RESULT_CANCELED, _result)
            finish()
        }
    }
}