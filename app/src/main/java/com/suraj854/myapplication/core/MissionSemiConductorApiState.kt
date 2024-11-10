package com.suraj854.myapplication.core

import kotlinx.coroutines.flow.MutableStateFlow


sealed class MissionSemiConductorApiState<out T> {
    class Success<T>(val data: T) : MissionSemiConductorApiState<T>()
    class Failure(val msg: Any) : MissionSemiConductorApiState<Nothing>()
    class NetworkError(val msg: String) : MissionSemiConductorApiState<Nothing>()
    data object Loading : MissionSemiConductorApiState<Nothing>()

    data object Empty : MissionSemiConductorApiState<Nothing>()
}