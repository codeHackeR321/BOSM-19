package com.dvm.appd.bosm.dbg.shared

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val modifiedRequest = chain.request().newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("HTTP_X_WALLET_TOKEN","ec123dac-339b-41ba-bca4-d3cab464083d")
            .addHeader("Authorization", "JWTeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IiIsInVzZXJfaWQiOjEsImV4cCI6MTU4MTc3MDczOCwidXNlcm5hbWUiOiJLYXp1bWEifQ.hOMAy_e_YnfD0eePAupMYPIZtnqbEU-Wfat97LDC3W4")
            .build()
        return chain.proceed(modifiedRequest)
        //HTTP_X_WALLET_TOKEN
    }
}