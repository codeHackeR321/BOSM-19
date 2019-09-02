package com.dvm.appd.bosm.dbg.shared

import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception

class MoneyTracker(val authRepository: AuthRepository) {

    private val db = FirebaseFirestore.getInstance()
    private val subject = BehaviorSubject.create<Int>()

    init {

        authRepository.getUser()
            .subscribe{
                db.collection("users").document(it.userId).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    val balance: Int
                    //TODO Determine correct default value
                    balance = try {
                        (documentSnapshot?.getLong("total_balance"))!!.toInt()
                    } catch (e: Exception) {
                        Integer.MAX_VALUE
                    }
                    subject.onNext(balance)
                }

            }

    }

    fun getBalance():Flowable<Int> = subject.toFlowable(BackpressureStrategy.LATEST)
}