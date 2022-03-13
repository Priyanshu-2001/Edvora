package com.geek.edvora.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils {
    companion object{

        fun isInternetAvailable(ctx: Context): Boolean {
            (ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    )?: false
                }else{
                    this.activeNetworkInfo?.isConnected ?: false
                }
            }

            //            val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetwork = cm.activeNetworkInfo
//            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}