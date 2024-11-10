package com.suraj854.healthquiz.presentation.quiz.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suraj854.healthquiz.core.MissionSemiConductorApiState
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.quizbank.dto.Questions
import com.suraj854.healthquiz.data.quizbank.dto.QuizBankData
import com.suraj854.healthquiz.data.quizbank.manager.RandomQuizGroupManager
import com.suraj854.healthquiz.domain.quiz.usecase.QuizAuthUseCase
import com.suraj854.healthquiz.presentation.generate_certificate.component.CertificateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizAuthUseCase,
    private val randomQuizGroupManager: RandomQuizGroupManager,
    private val userPreferences: UserPreferences
) : ViewModel() {

    companion object {
        const val TAG = "_QuizViewModel"
    }


    private val _response = MutableStateFlow<MissionSemiConductorApiState<QuizBankData?>>(
        MissionSemiConductorApiState.Empty
    )
    val response = _response.asStateFlow()


    private val _quizSaveResponse = MutableStateFlow<MissionSemiConductorApiState<Boolean?>>(
        MissionSemiConductorApiState.Empty
    )
    val quizSaveResponse = _quizSaveResponse.asStateFlow()

    fun saveCertificateRecords(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        quizUseCase.executeSaveCertificateData(
            context = context,
            email = userPreferences.getEmail() ?: "",

            url = userPreferences.getCertificateUrl() ?: ""
        ).collect { isCertificatedRecordSaved ->
            Log.e("record", "saved")


        }
    }

    fun loadQuestionBank(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        quizUseCase.executeQuizBank(context).collect { data ->

            val index = randomQuizGroupManager.getRandomIndex()
            val name = data?.quizBankData?.get(0)?.questions_list?.get(0)?.question
            Log.e(TAG, name ?: "null")


            _response.value = MissionSemiConductorApiState.Success(
                data = data?.quizBankData?.get(
                    index ?: 0
                )
            )


        }

    }

    fun saveQuiz(context: Context, quizBankData: List<Questions>) =
        viewModelScope.launch(Dispatchers.IO) {
            quizUseCase.executeSaveQuizData(context, quizBankData).onStart {

            }.collect { data ->
            }
        }

    fun generateCertificate(context: Context) =
        viewModelScope.launch(Dispatchers.IO) {
            _quizSaveResponse.value = MissionSemiConductorApiState.Loading
            val certificateView = CertificateView(context).generatePdf { success ->
                _quizSaveResponse.value = MissionSemiConductorApiState.Success(
                    true
                )
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


        }}


