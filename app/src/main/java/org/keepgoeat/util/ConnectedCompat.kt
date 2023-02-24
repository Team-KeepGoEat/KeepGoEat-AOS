package org.keepgoeat.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

object ConnectedCompat {
    private val IMPL: ConnectedCompatImpl = MarshMallowImpl

    fun isConnected(connectivityManager: ConnectivityManager): Boolean =
        IMPL.isConnected(connectivityManager)

    internal interface ConnectedCompatImpl {
        fun isConnected(connectivityManager: ConnectivityManager): Boolean
    }

    object MarshMallowImpl : ConnectedCompatImpl {
        override fun isConnected(connectivityManager: ConnectivityManager): Boolean =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NET_CAPABILITY_INTERNET) == true
    }
}
