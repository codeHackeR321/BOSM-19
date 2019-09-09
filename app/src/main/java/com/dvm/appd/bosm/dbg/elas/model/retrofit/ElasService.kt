package com.dvm.appd.bosm.dbg.elas.model.retrofit

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ElasService {

    @POST
    fun answerQuestion(@Header("Authorization") jwt: String, @Body body: JsonObject): Single<Response<AnswerResponse>>

    @GET
    fun getUserLeaderboardPosition(@Header("Authorization") jwt: String): Single<Response<PlayerRankingResponse>>

}