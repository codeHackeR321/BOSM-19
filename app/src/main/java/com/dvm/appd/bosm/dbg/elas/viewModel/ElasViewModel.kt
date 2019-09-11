package com.dvm.appd.bosm.dbg.elas.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import com.dvm.appd.bosm.dbg.elas.model.retrofit.PlayerRankingResponse
import com.dvm.appd.bosm.dbg.shared.util.asMut
import com.dvm.appd.bosm.dbg.splash.views.UiState
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class ElasViewModel(val elasRepository: ElasRepository) : ViewModel() {
    private val TAG = "ELAS REPO"

    var questions: LiveData<Map<Long, List<CombinedQuestionOptionDataClass>>> = MutableLiveData()
    var activeQuestion: LiveData<List<CombinedQuestionOptionDataClass>> = MutableLiveData()
    var uiState: LiveData<UIStateElas> =  MutableLiveData()
    var compositeDisposable = CompositeDisposable()
    var rulesForCurrentRound: LiveData<List<String>> = MutableLiveData()
    var leaderboard: LiveData<List<PlayerRankingResponse>> = MutableLiveData()

    init {
        uiState.asMut().postValue(UIStateElas.Loading)
        getQuestions()
        getLeaderboard()
    }

    @SuppressLint("CheckResult")
    private fun getLeaderboard() {
        val d2 = elasRepository.getLeaderboardFromRoom().subscribe({
            Log.d("Elas VoewModel", "Observer for leaderboard entered with = ${it.toString()}")
            leaderboard.asMut().postValue(it)
        },{
            Log.e("ELASQViewModel", "Error in reading Leaderboard from room = ${it.message.toString()}")
            uiState.asMut().postValue(UIStateElas.Failure("Failed to recive data from server. Try Again"))
        })
        compositeDisposable.add(d2)
    }

    private fun getQuestions() {
        val d1 = elasRepository.getQuestionsFromRoom().doOnNext{
            Log.d(TAG, "Entered Observer with list = ${it.toString()}")
            uiState.asMut().postValue(UIStateElas.Questions(it.groupBy { it.questionId }))
            (questions as MutableLiveData<Map<Long, List<CombinedQuestionOptionDataClass>>>).postValue(it.groupBy { it.questionId })
        }.doOnError{
            Log.e(TAG, "Error in reading from room = ${it.message.toString()}")
            uiState.asMut().postValue(UIStateElas.Failure("Failed to recive data from server. Try Again"))
            reinitializeSubscription()
        }.subscribe()
        compositeDisposable.add(d1)
    }

    private fun reinitializeSubscription() {
        compositeDisposable.dispose()
        getQuestions()
        getLeaderboard()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}