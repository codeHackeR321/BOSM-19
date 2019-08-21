package com.dvm.appd.bosm.dbg.more.dataClasses

import android.provider.ContactsContract

data class Contact(
    val name: String,
    val role: String,
    val email: String,
    val phone: String,
    val imageLink: String
)