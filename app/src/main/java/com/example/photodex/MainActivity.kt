package com.example.photodex

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.photodex.databinding.ActivityMainBinding
import com.example.photodex.receivers.NetworkReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val networkReceiver = NetworkReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Nav Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestNotificationPermission()
        }

    // 🚨🚨🚨 AI ALERT 🚨🚨🚨
    /*
    * I couldn't get network receiver to work, as it turns out it is now impossible to register it in manifest,
    * and it turned out it does not work without it.
    * Documentation was pretty much useless as the up to date way to manage it is via connectivityManager.requestNetwork(networkRequest, networkCallback)
    * The clanker suggested registering receiver in MainActivity and it works so cannot complain.
     */
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkReceiver)
    }

    // 🚨🚨🚨 AI ALERT END 🚨🚨🚨

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }
    }

