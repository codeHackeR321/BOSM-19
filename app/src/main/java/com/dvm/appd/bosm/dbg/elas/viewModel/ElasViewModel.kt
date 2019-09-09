package com.dvm.appd.bosm.dbg.elas.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import com.dvm.appd.bosm.dbg.shared.util.asMut
import io.reactivex.disposables.CompositeDisposable

class ElasViewModel(val elasRepository: ElasRepository) : ViewModel() {
    private val TAG = "ELAS REPO"

    var questions: LiveData<Map<Long, List<CombinedQuestionOptionDataClass>>> = MutableLiveData()
    var activeQuestion: LiveData<List<CombinedQuestionOptionDataClass>> = MutableLiveData()
    var uiState: LiveData<UIStateElas> =  MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    init {
        uiState.asMut().postValue(UIStateElas.Loading)
        getQuestions()
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

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}