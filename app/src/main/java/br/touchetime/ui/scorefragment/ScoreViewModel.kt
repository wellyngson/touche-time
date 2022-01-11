package br.touchetime.ui.scorefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.extension.changeTimer
import br.touchetime.model.UiState
import br.touchetime.model.UiStateScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScoreViewModel : ViewModel() {

    private val _scoreRed = MutableStateFlow(UiStateScore(0, UiState.Initial))
    private val _scoreBlue = MutableStateFlow(UiStateScore(0, UiState.Initial))
    private val _technicalSuperiority = MutableStateFlow(UiStateScore(8, UiState.Initial))

    val scoreRed: StateFlow<UiStateScore<Int>> get() = _scoreRed
    val scoreBlue: StateFlow<UiStateScore<Int>> get() = _scoreBlue
    val technicalSuperiority: StateFlow<UiStateScore<Int>> get() = _technicalSuperiority

    // Change Red

    fun addScoreRed() = viewModelScope.launch {
        var scoreRedInitial = _scoreRed.value.score

        _scoreRed.value = UiStateScore(++scoreRedInitial, UiState.Success)

        if (checkVictory(scoreRedInitial, _scoreBlue.value.score)) {
            _scoreRed.value = UiStateScore(scoreRedInitial, UiState.Finish)
        }
    }

    fun removeScoreRed() = viewModelScope.launch {
        val scoreRedInitial = _scoreRed.value.score

        if (scoreRedInitial > 0) {
            _scoreRed.value = UiStateScore(scoreRedInitial - 1, UiState.Success)
        } else {
            _scoreRed.value = UiStateScore(scoreRedInitial, UiState.Error)
        }
    }

    // Change Blue

    fun addScoreBlue() = viewModelScope.launch {
        var scoreBlueInitial = _scoreBlue.value.score

        _scoreBlue.value = UiStateScore(++scoreBlueInitial, UiState.Success)

        if (checkVictory(scoreBlueInitial, _scoreRed.value.score)) {
            _scoreBlue.value = UiStateScore(scoreBlueInitial, UiState.Finish)
        }
    }

    fun removeScoreBlue() = viewModelScope.launch {
        val scoreBlueInitial = _scoreBlue.value.score

        if (scoreBlueInitial > 0) {
            _scoreBlue.value = UiStateScore(scoreBlueInitial - 1, UiState.Success)
        } else {
            _scoreBlue.value = UiStateScore(scoreBlueInitial, UiState.Error)
        }
    }

    // Technical Superiority Change

    fun changeParameters() = viewModelScope.launch {
        val scoreRedInitial = scoreRed.value.score
        val scoreBlueInitial = scoreBlue.value.score

        _technicalSuperiority.value = UiStateScore(
            technicalSuperiorityEditFight.value.score,
            UiState.Success
        )

        if (checkVictory(scoreRedInitial, scoreBlueInitial)) {
            _scoreRed.value = UiStateScore(scoreRedInitial, UiState.Finish)
        } else if (checkVictory(scoreBlueInitial, scoreRedInitial)) {
            _scoreBlue.value = UiStateScore(scoreBlueInitial, UiState.Finish)
        }
    }

    private fun checkVictory(scoreAthleteOne: Int, scoreAthleteTwo: Int): Boolean {
        val technicalSuperiorityInitial = _technicalSuperiority.value.score

        return (scoreAthleteOne - scoreAthleteTwo) >= technicalSuperiorityInitial
    }

    // EditFightFragment

    private val _technicalSuperiorityEditFight = MutableStateFlow(UiStateScore(8, UiState.Initial))
    private val _numberRoundEditFight = MutableStateFlow(UiStateScore(2, UiState.Initial))
    private val _timeRoundEditFight = MutableStateFlow(UiStateScore("03:00", UiState.Initial))
    private val _timeIntervalEditFight = MutableStateFlow(UiStateScore("00:30", UiState.Initial))

    val technicalSuperiorityEditFight: StateFlow<UiStateScore<Int>> get() = _technicalSuperiorityEditFight
    val numberRoundEditFight: StateFlow<UiStateScore<Int>> get() = _numberRoundEditFight
    val timeRoundEditFight: StateFlow<UiStateScore<String>> get() = _timeRoundEditFight
    val timeIntervalEditFight: StateFlow<UiStateScore<String>> = _timeIntervalEditFight

    fun addTechnicalSuperiority() = viewModelScope.launch {
        var technicalSuperiorityFinal = _technicalSuperiorityEditFight.value.score

        _technicalSuperiorityEditFight.value = UiStateScore(++technicalSuperiorityFinal, UiState.Success)
    }

    fun removeTechnicalSuperiority() = viewModelScope.launch {
        var technicalSuperiorityFinal = _technicalSuperiorityEditFight.value.score

        if (technicalSuperiorityFinal > 1) {
            _technicalSuperiorityEditFight.value = UiStateScore(--technicalSuperiorityFinal, UiState.Success)
        } else {
            _technicalSuperiorityEditFight.value = UiStateScore(1, UiState.Success)
        }
    }

    fun addNumberRounds() = viewModelScope.launch {
        var numberRoundFinal = _numberRoundEditFight.value.score

        _numberRoundEditFight.value = UiStateScore(++numberRoundFinal, UiState.Success)
    }

    fun removeNumberRound() = viewModelScope.launch {
        var numberRoundFinal = _numberRoundEditFight.value.score

        if (numberRoundFinal > 1) {
            _numberRoundEditFight.value = UiStateScore(--numberRoundFinal, UiState.Success)
        } else {
            _numberRoundEditFight.value = UiStateScore(1, UiState.Success)
        }
    }

    fun addTimeRound() = viewModelScope.launch {
        val minutes = _timeRoundEditFight.value.score.substring(0, 2).toInt()
        val seconds = _timeRoundEditFight.value.score.substring(3).toInt()

        _timeRoundEditFight.value = UiStateScore(changeTimer(minutes, seconds, 10), UiState.Success)
    }

    fun removeTimeRound() = viewModelScope.launch {
        val minutes = _timeRoundEditFight.value.score.substring(0, 2).toInt()
        val seconds = _timeRoundEditFight.value.score.substring(3).toInt()

        _timeRoundEditFight.value = UiStateScore(changeTimer(minutes, seconds, -10), UiState.Success)
    }

    fun addTimeInterval() {
        val minutes = _timeIntervalEditFight.value.score.substring(0, 2).toInt()
        val seconds = _timeIntervalEditFight.value.score.substring(3).toInt()

        _timeIntervalEditFight.value = UiStateScore(changeTimer(minutes, seconds, 10), UiState.Success)
    }

    fun removeTimeInterval() {
        val minutes = _timeIntervalEditFight.value.score.substring(0, 2).toInt()
        val seconds = _timeIntervalEditFight.value.score.substring(3).toInt()

        _timeIntervalEditFight.value = UiStateScore(changeTimer(minutes, seconds, -10), UiState.Success)
    }
}
