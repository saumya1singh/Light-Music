package com.example.lightmusicapp

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor?= null
    var sensorManager: SensorManager?= null

    // if the sensor is running or not
    var isRunning= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    // register a listener for the sensor
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // unregister the sensor when activity is paused
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var lightValue= event!!.values[0]

        if(lightValue > 30 && !isRunning){
            isRunning=true
            try{
                var mp= MediaPlayer.create(this, R.raw.music)
                mp.setVolume(1f, 1f)
                mp.start()
//                var mp= MediaPlayer()
//                mp.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
//                mp.prepare()
//                mp.start()
            }catch (e: IOException){
                Log.d(TAG, "onSensorChanged: ${e.message} " )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}