package br.touchetime.ui.editfightfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.ParamsFight
import br.touchetime.data.model.UiState
import br.touchetime.data.model.UiStateScore
import br.touchetime.data.repository.ScoreRepository
import br.touchetime.extension.changeTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFightViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

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

    fun changeParameters(
        paramsFight: ParamsFight,
    ) = viewModelScope.launch {

        scoreRepository.changeParams(paramsFight)
    }
}
