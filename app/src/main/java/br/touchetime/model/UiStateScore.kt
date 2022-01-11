package br.touchetime.model

data class UiStateScore<T> (
    val score: T,
    val state: UiState
)

enum class UiState {
    Initial,
    Success,
    Finish,
    Error
}

// sealed class UiStateScore<T> {
//    data class Initial<T>(val score: T) : UiStateScore<T>()
//    data class Success<T>(val score: T) : UiStateScore<T>()
//    data class Finish<T>(val score: T) : UiStateScore<T>()
//    data class Error<T>(val score: T) : UiStateScore<T>()
// }
