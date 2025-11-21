package com.example.photodex.receivers

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import com.example.photodex.viewmodel.NetworkViewModel

class NetworkReceiver: BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active = cm.activeNetworkInfo
        val isConnected = active?.isConnected == true

        NetworkViewModel.isNetworkAvailable.value = isConnected
    }

}