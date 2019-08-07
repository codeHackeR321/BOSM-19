package com.dvm.appd.bosm.dbg.wallet.data.retrofit

import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.AllOrdersPojo
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface WalletService {

    @GET("wallet/vendors")
    fun getAllStalls():Single<Response<List<StallsPojo>>>

    @GET("wallet/orders")
    @Headers("Authorization: JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1NjUwMzQ3MDQsInVzZXJuYW1lIjoiYXBwZCIsImVtYWlsIjoiIn0.terKtruR883sHL12PffRuY1BfMOggYkaNnRE1Gn6Dmc")
    fun getAllOrders(): Single<Response<List<AllOrdersPojo>>>

    @POST("wallet/orders")
    @Headers("Authorization: JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1NjUwMzQ3MDQsInVzZXJuYW1lIjoiYXBwZCIsImVtYWlsIjoiIn0.terKtruR883sHL12PffRuY1BfMOggYkaNnRE1Gn6Dmc")
    fun placeOrder(@Body body: JsonObject): Single<Response<AllOrdersPojo>>
}