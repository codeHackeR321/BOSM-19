package com.dvm.appd.bosm.dbg.wallet.data.retrofit

import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.AllOrdersPojo
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface WalletService {

    @GET("wallet/vendors")
    fun getAllStalls():Single<Response<List<StallsPojo>>>

    @GET("wallet/orders")
    @Headers("Authorization: JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFwcGQiLCJleHAiOjE1NjY4ODc1NTgsImVtYWlsIjoiIn0.4rTDztFegM-cRSRE9-pRR3mj0kWGDintUUXAhrAST7E")
    fun getAllOrders(): Single<Response<List<AllOrdersPojo>>>

    @POST("wallet/orders")
    @Headers("Authorization: JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFwcGQiLCJleHAiOjE1NjY4ODc1NTgsImVtYWlsIjoiIn0.4rTDztFegM-cRSRE9-pRR3mj0kWGDintUUXAhrAST7E")
    fun placeOrder(@Body body: JsonObject): Single<Response<AllOrdersPojo>>

    @POST("wallet/orders/make_otp_seen")
    @Headers("Authorization: JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFwcGQiLCJleHAiOjE1NjY4ODc1NTgsImVtYWlsIjoiIn0.4rTDztFegM-cRSRE9-pRR3mj0kWGDintUUXAhrAST7E")
    fun makeOtpSeen(@Body body: JsonObject): Single<Response<Unit>>
}