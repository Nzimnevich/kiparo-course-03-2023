package com.example.lesson1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivityMainBinding

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, PlaybackService::class.java)

        binding.btnStartService.apply {
            this.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent) // этой командой обещаем системе что запустим сервис//это команда onStartCommand в др классе
                } else {
                    startService(intent)
                }
            }
        }

        binding.btnStopService.apply {
            this.setOnClickListener {
                //stopService(intent)
               // startSecondActivity()
                startSite()
            }
        }

        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
//        val x = 5
//        val y = 10
//        val s = x + y
//
//        Log.d(TAG, "onCreate $x")
//        Log.d(TAG, "onCreate2 $y")
//        Log.d(TAG, "onCreate3 $s")
//        Log.d(TAG, "onCreate4")
//        Log.d(TAG, "onCreate5 $x")
//        Log.d(TAG, "onCreate6")

        Log.d(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    //явный интент
    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java) // система создает активити
        intent.putExtra(SecondActivity.KEY,"Value from MainActivity")
        startActivity(intent)
    }

    //неявный интент
    private  fun startSite(){
        val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
        startActivity(intent)
    }
}
