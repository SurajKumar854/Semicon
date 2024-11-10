package com.suraj854.myapplication.presentation.signup.screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suraj854.myapplication.core.Constants
import com.suraj854.myapplication.core.MissionSemiConductorApiState
import com.suraj854.myapplication.data.common.UserPreferences
import com.suraj854.myapplication.data.common.Utils
import com.suraj854.myapplication.data.signup.dto.SignUpResponse
import com.suraj854.myapplication.domain.signup.entities.User
import com.suraj854.myapplication.domain.signup.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    companion object {
        const val TAG = "_SignUpViewModel"

    }


    private val _response = MutableStateFlow<MissionSemiConductorApiState<SignUpResponse?>>(
        MissionSemiConductorApiState.Empty
    )
    val response = _response.asStateFlow()

    fun signUpUser(context: Context, user: User) = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.clearUserData()
        _response.value = MissionSemiConductorApiState.Loading
        if (Utils.isNetworkConnected()) {
            signUpUseCase.executeSignUp(context, user).onStart {

            }.catch {
                _response.value = MissionSemiConductorApiState.Failure("Unknown error occurred")

            }
                .collect { data ->
                    if (data != null) {
                        _response.value = MissionSemiConductorApiState.Success(data = data)

                    }
                }
        } else {
            _response.value =
                MissionSemiConductorApiState.NetworkError(Constants.InternetConnectivityMSG)
        }


    }

}


