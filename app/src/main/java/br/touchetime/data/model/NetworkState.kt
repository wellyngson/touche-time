package br.touchetime.data.model

import java.lang.Exception

sealed class NetworkState {
    object Idle : NetworkState()
    object Loaded : NetworkState()
    object Loading : NetworkState()
    data class Failed(val exception: Exception) : NetworkState()
}
