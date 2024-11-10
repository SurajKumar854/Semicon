package com.suraj854.healthquiz.core


sealed class MissionSemiConductorApiState<out T> {
    class Success<T>(val data: T) : MissionSemiConductorApiState<T>()
    class Failure(val msg: Any) : MissionSemiConductorApiState<Nothing>()
    class NetworkError(val msg: String) : MissionSemiConductorApiState<Nothing>()
    data object Loading : MissionSemiConductorApiState<Nothing>()

    data object Empty : MissionSemiConductorApiState<Nothing>()
}