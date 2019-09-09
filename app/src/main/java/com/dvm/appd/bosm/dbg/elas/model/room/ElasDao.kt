package com.dvm.appd.bosm.dbg.elas.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ElasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestions(questions: List<QuestionData>): Single<Unit>

    @Insert
    fun insertOptions(options: List<OptionData>): Single<Unit>

    @Query("DELETE FROM option_table WHERE questionId = :id")
    fun deleteOptionsForQuestionWithId(id: Long): Single<Unit>

    @Query("DELETE FROM option_table")
    fun deleteAllOptions(): Single<Unit>

    @Query("SELECT question_table.questionId, option_table.option_id, option_table.option, question_table.question, question_table.category FROM question_table JOIN option_table ON question_table.questionId = option_table.questionId WHERE question_table.questionId = :id")
    fun selectParticularQuestionRoom(id: Long): Single<List<CombinedQuestionOptionDataClass>>

    @Query("SELECT question_table.questionId, option_table.option_id, option_table.option, question_table.question, question_table.category FROM question_table JOIN option_table ON question_table.questionId = option_table.questionId WHERE category = :category ")
    fun selectQuestionsInCategory(category: String): Flowable<List<CombinedQuestionOptionDataClass>>

    @Query("SELECT question_table.questionId, option_table.option_id, option_table.option, question_table.question, question_table.category FROM question_table JOIN option_table ON question_table.questionId = option_table.questionId")
    fun getAllQuestions(): Flowable<List<CombinedQuestionOptionDataClass>>
}