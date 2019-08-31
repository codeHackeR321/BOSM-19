package com.dvm.appd.bosm.dbg.shared

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.dvm.appd.bosm.dbg.NetworkChangeNotifier
import com.google.android.material.snackbar.Snackbar

class NetworkChangeReciver(val listener: NetworkChangeNotifier) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        listener.onNetworkStatusScahnged(NetworkChecker(context).isConnected())
    }
}
