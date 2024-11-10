package com.suraj854.healthquiz.data.quizbank.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.common.Utils
import com.suraj854.healthquiz.data.quizbank.dto.Questions
import com.suraj854.healthquiz.data.quizbank.dto.QuizResponse
import com.suraj854.healthquiz.domain.quiz.QuizRepository
import com.suraj854.healthquiz.domain.quiz.entities.CertificateRecord
import com.suraj854.healthquiz.domain.quiz.entities.QuizRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuizRepositoryIml @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userPreferences: UserPreferences
) : QuizRepository {


    override suspend fun loadQuiz(context: Context): Flow<QuizResponse> {
        return flow {
            val filename = "question_bank.json"
            val jsonData = Utils.readJsonFromAssets(context, filename)
            val gson = Gson()
            val data = gson.fromJson(jsonData, QuizResponse::class.java)
            emit(data)

        }
    }

    override suspend fun saveQuiz(
        context: Context,
        quizResponse: List<Questions>
    ): Flow<Boolean> {
        return flow {
            var correctAnswers = 0
            var score = 0f
            val quizRecord =
                QuizRecord(userEmail = userPreferences.getEmail() ?: "null", quizResponse)

            quizRecord.quizQues.forEachIndexed { index, questions ->
                if (questions.selectedOptionID == questions.correct_option.option_id) {
                    correctAnswers++
                }
            }

            score = ((correctAnswers / (quizRecord.quizQues.size).toFloat()) * 100) / 100
            userPreferences.saveQuizScore(score)
            firestore.collection("user_quiz_data").add(quizRecord).await()
            emit(true)

        }
    }

    override suspend fun saveCertificateUrlData(
        context: Context,
        email: String,
        url: String
    ): Flow<Boolean> {
        return flow {
            val certificateRecord = CertificateRecord(email, url)
            firestore.collection("user_certificate_data").add(certificateRecord).await()
           Log.e("Certificate","Saved")
            emit(true)

        }
    }


}