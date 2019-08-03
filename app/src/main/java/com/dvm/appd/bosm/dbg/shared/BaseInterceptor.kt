package com.dvm.appd.bosm.dbg.shared

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val modifiedRequest = chain.request().newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("X-Wallet-Token","ec123dac-339b-41ba-bca4-d3cab464083d")
            .addHeader("Authorization", "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJlbWFpbCI6IiIsInVzZXJuYW1lIjoiYXBwZCIsImV4cCI6MTU2NDg0MDM5NH0.6JNSG_VEsvSxxN05hHuo8CCCyV0AQW83CyJVF9xRqrM")
            .build()
        return chain.proceed(modifiedRequest)
        //HTTP_X_WALLET_TOKEN
    }
}