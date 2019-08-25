package com.example.sensorapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.Menu
import android.view.MenuItem
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sensorView: TextView = findViewById(R.id.sensorView)

        val sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        var str = ""

        for (s: Sensor in sensorList) {
            str += getString(R.string.sensor_name) + s.name + "\n"
            str += getString(R.string.manufacture_name) + s.vendor + "\n"
            str += getString(R.string.version) + s.version + "\n"
            str += getString(R.string.power_consumption) + s.power + "mA\n"
            str += getString(R.string.maximum_range) + s.maximumRange + "\n"
            str += getString(R.string.minimum_delay) + s.minDelay + "μs\n"
            str += getString(R.string.maximum_delay) + s.maxDelay + "μs\n"
            str += "-----------------------------------------------" + "\n"
        }
        sensorView.text = str
    }

    //メニュー関連
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.copy -> {

                val sensorView: TextView = findViewById(R.id.sensorView)
                when (copyToClipboard(sensorView.text.toString())) {
                    true -> {
                        // OKだった場合
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.copy_ok),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        // NGだった場合
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.copy_ng),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        return false
    }

    private fun copyToClipboard(text: String): Boolean {
        //センサー一覧表示用のテキストボックスを取得
        try {
            val clipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
        } catch (e: Exception) {
            return false
        }
        return true
    }
}