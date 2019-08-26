package com.dvm.appd.bosm.dbg.shared

import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class MoneyTracker(val authRepository: AuthRepository) {

    private val db = FirebaseFirestore.getInstance()
    private val subject = BehaviorSubject.create<Int>()

    init {

        authRepository.getUser()
            .subscribe{
                db.collection("users").document(it.userId).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    val balance = documentSnapshot?.getLong("total_balance")
                    subject.onNext(balance!!.toInt())
                }

            }

    }

    fun getBalance():Flowable<Int> = subject.toFlowable(BackpressureStrategy.LATEST)
}