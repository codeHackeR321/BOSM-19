package com.dvm.appd.bosm.dbg.auth.data

data class User(
    val jwt: String,
    val name: String,
    val userId: String,
    val email: String,
    val phone: String,
    val qrCode: String,
    val isBitsian:Boolean,
    val firstLogin:Boolean
)