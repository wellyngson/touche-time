package br.touchetime.ui.scorefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.UiState
import br.touchetime.data.model.UiStateScore
import br.touchetime.data.repository.ScoreRepository
import br.touchetime.extension.checkVictory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _scoreRed = MutableStateFlow(UiStateScore(scoreRepository.getScoreRed(), UiState.Initial))
    private val _scoreBlue = MutableStateFlow(UiStateScore(scoreRepository.getScoreBlue(), UiState.Initial))
    private val _technicalSuperiority = MutableStateFlow(UiStateScore(scoreRepository.getTechnicalSuperiority(), UiState.Initial))

    val scoreRed: StateFlow<UiStateScore<Int>> get() = _scoreRed
    val scoreBlue: StateFlow<UiStateScore<Int>> get() = _scoreBlue
    val technicalSuperiority: StateFlow<UiStateScore<Int>> get() = _technicalSuperiority

    // Change Red

    fun addScoreRed() = viewModelScope.launch {
        val scoreRedRepository = scoreRepository.addScoreRed()
        _scoreRed.value = UiStateScore(scoreRedRepository, UiState.Success)

        if (checkVictory(
                scoreRedRepository,
                _scoreBlue.value.score,
                scoreRepository.getTechnicalSuperiority()
            )
        ) {
            _scoreRed.value = UiStateScore(scoreRedRepository, UiState.Finish)
        }
    }

    fun removeScoreRed() = viewModelScope.launch {
        val scoreRedInitial = scoreRepository.getScoreRed()

        if (scoreRedInitial > 0) {
            _scoreRed.value = UiStateScore(scoreRepository.removeScoreRed(), UiState.Success)
        } else {
            _scoreRed.value = UiStateScore(scoreRedInitial, UiState.Error)
        }
    }

    // Change Blue

    fun addScoreBlue() = viewModelScope.launch {
        val scoreBlueRepository = scoreRepository.addScoreBlue()
        _scoreBlue.value = UiStateScore(scoreBlueRepository, UiState.Success)

        if (checkVictory(
                scoreBlueRepository,
                _scoreRed.value.score,
                scoreRepository.getTechnicalSuperiority()
            )
        ) {
            _scoreBlue.value = UiStateScore(scoreBlueRepository, UiState.Finish)
        }
    }

    fun removeScoreBlue() = viewModelScope.launch {
        val scoreBlueInitial = scoreRepository.getScoreBlue()

        if (scoreBlueInitial > 0) {
            _scoreBlue.value = UiStateScore(scoreRepository.removeScoreBlue(), UiState.Success)
        } else {
            _scoreBlue.value = UiStateScore(scoreBlueInitial, UiState.Error)
        }
    }

    // Params

    fun getTimeRound(): String = scoreRepository.getTimeRound()

    fun getTimeInterval(): String = scoreRepository.getTimeInterval()
}
