package com.dvm.appd.bosm.dbg.wallet.data.retrofit

import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.AllOrdersPojo
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface WalletService {

    @GET("wallet/vendors")
    fun getAllStalls():Single<Response<List<StallsPojo>>>

    @GET("wallet/orders/1")
    fun getAllOrders(): Single<Response<List<AllOrdersPojo>>>

    @POST("wallet/orders")
    fun placeOrder(@Body body: JsonObject): Single<Response<List<AllOrdersPojo>>>
}