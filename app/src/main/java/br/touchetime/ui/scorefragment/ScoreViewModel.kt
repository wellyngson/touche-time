package br.touchetime.ui.scorefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScoreViewModel : ViewModel() {

    private val mutableScoreRed = MutableStateFlow<UiState>(UiState.Initial(0))
    private val mutableScoreBlue = MutableStateFlow<UiState>(UiState.Initial(0))
    private val mutableTechnicalSuperiority = MutableStateFlow<UiState>(UiState.Initial(8))

    val scoreRed: StateFlow<UiState> get() = mutableScoreRed
    val scoreBlue: StateFlow<UiState> get() = mutableScoreBlue
    val technicalSuperiority: StateFlow<UiState> get() = mutableTechnicalSuperiority

    // Change Red

    fun addScoreRed() = viewModelScope.launch {
        var scoreRedInitial = mutableScoreRed.value.hashCode()

        mutableScoreRed.value = UiState.Success(++scoreRedInitial)

        if (checkVictory(scoreRedInitial, mutableScoreBlue.value.hashCode())) {
            mutableScoreRed.value = UiState.Finish(scoreRedInitial)
        }
    }

    fun removeScoreRed() = viewModelScope.launch {
        val scoreRedInitial = mutableScoreRed.value.hashCode()

        if (scoreRedInitial > 0) {
            mutableScoreRed.value = UiState.Success(scoreRedInitial - 1)
        } else {
            mutableScoreRed.value = UiState.Error(scoreRedInitial)
        }
    }

    // Change Blue

    fun addScoreBlue() = viewModelScope.launch {
        var scoreBlueInitial = mutableScoreBlue.value.hashCode()

        mutableScoreBlue.value = UiState.Success(++scoreBlueInitial)

        if (checkVictory(scoreBlueInitial, mutableScoreRed.value.hashCode())) {
            mutableScoreBlue.value = UiState.Finish(scoreBlueInitial)
        }
    }

    fun removeScoreBlue() = viewModelScope.launch {
        val scoreBlueInitial = mutableScoreBlue.value.hashCode()

        if (scoreBlueInitial > 0) {
            mutableScoreBlue.value = UiState.Success(scoreBlueInitial - 1)
        } else {
            mutableScoreBlue.value = UiState.Error(scoreBlueInitial)
        }
    }

    // Technical Superiority

    private fun checkVictory(scoreAthleteOne: Int, scoreAthleteTwo: Int): Boolean {
        val technicalSuperiorityInitial = mutableTechnicalSuperiority.value.hashCode()

        return (scoreAthleteOne - scoreAthleteTwo) >= technicalSuperiorityInitial
    }

    sealed class UiState {
        data class Initial(val scoreInitial: Int) : UiState()
        data class Success(val score: Int) : UiState()
        data class Finish(val scoreFinal: Int) : UiState()
        data class Error(val score: Int) : UiState()
    }
}
