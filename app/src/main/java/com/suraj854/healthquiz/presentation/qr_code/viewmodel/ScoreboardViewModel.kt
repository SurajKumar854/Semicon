package com.suraj854.healthquiz.presentation.qr_code.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.suraj854.healthquiz.core.MissionSemiConductorApiState
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.quizbank.dto.Questions
import com.suraj854.healthquiz.data.quizbank.dto.QuizBankData
import com.suraj854.healthquiz.domain.quiz.usecase.QuizAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreBoardViewModel @Inject constructor(
    private val quizAuthUseCase: QuizAuthUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    companion object {
        const val TAG = "_QuizViewModel"
    }


    private val _response = MutableStateFlow<MissionSemiConductorApiState<QuizBankData?>>(
        MissionSemiConductorApiState.Empty
    )

    fun saveQuiz(context: Context, quizBankData: List<Questions>) =
        viewModelScope.launch(Dispatchers.IO) {
            quizAuthUseCase.executeSaveQuizData(context, quizBankData).onStart {

            }.collect { data ->
            }


            /* launch {
                 quizUseCase.executeSaveCertificateData(
                     context = context,
                     userPreferences.getEmail() ?: "",
                     userPreferences.getCertificateUrl() ?: ""
                 ).collect { data ->
                     Log.e("Tag", "certificate records")
                     _quizSaveResponse.value = MissionSemiConductorApiState.Success(
                         data
                     )
                 }

             }*/


        }

    val response = _response.asStateFlow()

    fun updateScoreInUserRecord() = viewModelScope.launch(Dispatchers.IO) {
        val firebase = FirebaseFirestore.getInstance()
        val query = firebase.collection("users").whereEqualTo("email", userPreferences.getEmail())
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result;
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        // Get the document ID
                        val documentId = document.id
                        val docRef: DocumentReference =
                            firebase.collection("users").document(documentId)


                        val updates: MutableMap<String, Any> = HashMap()
                        val scoreValue = Math.round((userPreferences.getQuizScore() ?: 0f) * 100);
                        updates["score"] = "$scoreValue"// Replace with your field name and value

                        docRef.update(updates).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Log.e("Firebase", "Score updated Successfully")
                            }

                        }

                        // Update the document with a new field

                    }
                }
            }

        }

    }
}