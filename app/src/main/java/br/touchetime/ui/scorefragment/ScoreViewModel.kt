package br.touchetime.ui.scorefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.extension.changeTimer
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

    fun changeParameters() {
        val scoreRedInitial = scoreRed.value.hashCode()
        val scoreBlueInitial = scoreBlue.value.hashCode()

        mutableTechnicalSuperiority.value = UiState.Success(technicalSuperiorityEditFight.value!!)

        if (checkVictory(scoreRedInitial, scoreBlueInitial)) {
            mutableScoreRed.value = UiState.Finish(scoreRedInitial)
        } else if (checkVictory(scoreBlueInitial, scoreRedInitial)) {
            mutableScoreBlue.value = UiState.Finish(scoreBlueInitial)
        }
    }

    sealed class UiState {
        data class Initial(val scoreInitial: Int) : UiState()
        data class Success(val score: Int) : UiState()
        data class Finish(val scoreFinal: Int) : UiState()
        data class Error(val score: Int) : UiState()
    }

// EditFightFragment

    private val mutableTechnicalSuperiorityEditFight = MutableLiveData(1)
    private val mutableNumberRoundEditFight = MutableLiveData(1)
    private val mutableTimeRoundEditFight = MutableLiveData("03:00")
    private val mutableTimeIntervalEditFight = MutableLiveData("00:30")

    val technicalSuperiorityEditFight: LiveData<Int> = mutableTechnicalSuperiorityEditFight
    val numberRoundEditFight: LiveData<Int> = mutableNumberRoundEditFight
    val timeRoundEditFight: LiveData<String> = mutableTimeRoundEditFight
    val timeIntervalEditFight: LiveData<String> = mutableTimeIntervalEditFight

    fun addTechnicalSuperiority() {
        var technicalSuperiorityFinal = mutableTechnicalSuperiorityEditFight.value!!

        mutableTechnicalSuperiorityEditFight.postValue(++technicalSuperiorityFinal)
    }

    fun removeTechnicalSuperiority() {
        var technicalSuperiorityFinal = mutableTechnicalSuperiorityEditFight.value!!

        if (technicalSuperiorityFinal > 1) {
            mutableTechnicalSuperiorityEditFight.postValue(--technicalSuperiorityFinal)
        } else {
            mutableTechnicalSuperiorityEditFight.postValue(1)
        }
    }

    fun addNumberRounds() {
        var numberRoundFinal = mutableNumberRoundEditFight.value!!

        mutableNumberRoundEditFight.postValue(++numberRoundFinal)
    }

    fun removeNumberRound() {
        var numberRoundFinal = mutableNumberRoundEditFight.value!!

        if (numberRoundFinal > 1) {
            mutableNumberRoundEditFight.postValue(--numberRoundFinal)
        } else {
            mutableNumberRoundEditFight.postValue(1)
        }
    }

    fun addTimeRound() {
        val minutes = mutableTimeRoundEditFight.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeRoundEditFight.value!!.substring(3).toInt()

        mutableTimeRoundEditFight.postValue(changeTimer(minutes, seconds, 10))
    }

    fun addTimeInterval() {
        val minutes = mutableTimeIntervalEditFight.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeIntervalEditFight.value!!.substring(3).toInt()

        mutableTimeIntervalEditFight.postValue(changeTimer(minutes, seconds, 10))
    }

    fun removeTimeRound() {
        val minutes = mutableTimeRoundEditFight.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeRoundEditFight.value!!.substring(3).toInt()

        mutableTimeRoundEditFight.postValue(changeTimer(minutes, seconds, -10))
    }

    fun removeTimeInterval() {
        val minutes = mutableTimeIntervalEditFight.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeIntervalEditFight.value!!.substring(3).toInt()

        mutableTimeIntervalEditFight.postValue(changeTimer(minutes, seconds, -10))
    }
}
