package com.dvm.appd.bosm.dbg.auth.data.repo

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.dvm.appd.bosm.dbg.auth.data.User
import com.dvm.appd.bosm.dbg.auth.data.retrofit.AuthPojo
import com.dvm.appd.bosm.dbg.auth.data.retrofit.AuthService
import com.dvm.appd.bosm.dbg.auth.views.LoginState
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.security.Key

class AuthRepository(val authService: AuthService, val sharedPreferences: SharedPreferences) {

    object Keys {

        const val name = "NAME"
        const val email = "EMAIL"
        const val contact = "CONTACT"
        const val jwt = "JWT"
        const val userId = "ID"
        const val qrCode = "QR"
        const val isBitsian = "ISBITSIAN"
    }

    fun loginOutstee(username: String, password: String): Single<LoginState> {
        val body = JsonObject().also {
            it.addProperty("username", username)
            it.addProperty("password", password)
        }

        Log.d("check",body.toString())
       return login(body, false)
    }

    fun getUser(): Maybe<User> {
        val name = sharedPreferences.getString(Keys.name, null)
        val email = sharedPreferences.getString(Keys.email, null)
        val contact = sharedPreferences.getString(Keys.contact, null)
        val jwt = sharedPreferences.getString(Keys.jwt, null)
        val id = sharedPreferences.getString(Keys.userId, null)
        val qr = sharedPreferences.getString(Keys.qrCode, null)
        val bitsian = sharedPreferences.getBoolean(Keys.isBitsian,false)
        Log.d("checkSp", listOf(name, email, contact, jwt, qr,bitsian).toString())
        if (listOf(name, email, contact, jwt, qr).contains(null)) {
            return Maybe.empty()
        }
        return Maybe.just(User(name!!, email!!, contact!!, jwt!!, id!!, qr!!,bitsian))
    }

    @SuppressLint("ApplySharedPref")
    fun setUser(user:User?): Completable {
        return Completable.fromAction {
            sharedPreferences.edit().apply {
                putString(Keys.name, user?.name)
                putString(Keys.jwt, user?.jwt)
                putString(Keys.contact, user?.phone)
                putString(Keys.email, user?.email)
                putString(Keys.userId, user?.userId)
                putString(Keys.qrCode, user?.qrCode)
                putBoolean(Keys.isBitsian,user?.isBitsian!!)

            }.commit()
        }
    }

    fun login(body: JsonObject, bitsian: Boolean): Single<LoginState> {
        return authService.login(body)
            .flatMap { response ->
                Log.d("checkr", response.code().toString())

                when(response.code()){
                 200 -> {
                     Log.d("checkr", response.body().toString())
                     setUser(
                         User(
                            jwt =  response.body()!!.jwt,
                            name =  response.body()!!.name,
                             userId = response.body()!!.userId,
                             email = response.body()!!.email,
                            phone =  response.body()!!.phone,
                             qrCode = response.body()!!.qrCode,
                             isBitsian = bitsian
                         )
                     ).subscribe()
                     Single.just(LoginState.Success)
                 }
                in 400..499 -> Single.just(LoginState.Failure(response.errorBody()!!.string()))
                else -> Single.just(LoginState.Failure("Something went wrong!!"))
                }

            }.doOnError {
                Log.d("checkre",it.toString())
            }
            .subscribeOn(Schedulers.io())
    }
}