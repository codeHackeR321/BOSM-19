package com.dvm.appd.bosm.dbg.events.retrofit

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface EventsService {

    @GET("boco_portal/get_epc_article/{event}/{eventId}")
    fun getEpcArticle(@Path("event")event:String, @Path("eventId")id: String): Single<Response<EPCData>>
}