package org.keepgoeat.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import org.keepgoeat.util.extension.toStateFlow

/* ref :
https://developer.android.com/training/basics/network-ops/reading-network-state
https://medium.com/@bazzairvine/observing-your-network-connection-with-flow-1cdedf31780c */

class NetworkMonitor(private val context: Context, private val coroutineScope: CoroutineScope) {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: StateFlow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.toStateFlow(coroutineScope, false)
}
