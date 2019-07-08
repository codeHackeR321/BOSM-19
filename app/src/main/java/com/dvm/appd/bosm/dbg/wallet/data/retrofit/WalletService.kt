package com.dvm.appd.bosm.dbg.wallet.data.retrofit

import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface WalletService {

    @GET("wallet/vendor")
    fun getAllStalls():Single<Response<List<StallsPojo>>>
}