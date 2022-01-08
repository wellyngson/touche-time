package br.touchetime.model

sealed class UiStateScore {
    data class Initial(val scoreInitial: Int) : UiStateScore()
    data class Success(val score: Int) : UiStateScore()
    data class Finish(val scoreFinal: Int) : UiStateScore()
    data class Error(val score: Int) : UiStateScore()
}
