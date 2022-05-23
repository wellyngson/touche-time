package br.touchetime.data.model

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
