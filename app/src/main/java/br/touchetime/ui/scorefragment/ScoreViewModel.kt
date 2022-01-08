package br.touchetime.ui.scorefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.extension.changeTimer
import br.touchetime.model.UiStateScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScoreViewModel : ViewModel() {

    private val mutableScoreRed = MutableStateFlow<UiStateScore>(UiStateScore.Initial(0))
    private val mutableScoreBlue = MutableStateFlow<UiStateScore>(UiStateScore.Initial(0))
    private val mutableTechnicalSuperiority = MutableStateFlow<UiStateScore>(UiStateScore.Initial(8))

    val scoreRed: StateFlow<UiStateScore> get() = mutableScoreRed
    val scoreBlue: StateFlow<UiStateScore> get() = mutableScoreBlue
    val technicalSuperiority: StateFlow<UiStateScore> get() = mutableTechnicalSuperiority

    // Change Red

    fun addScoreRed() = viewModelScope.launch {
        var scoreRedInitial = mutableScoreRed.value.hashCode()

        mutableScoreRed.value = UiStateScore.Success(++scoreRedInitial)

        if (checkVictory(scoreRedInitial, mutableScoreBlue.value.hashCode())) {
            mutableScoreRed.value = UiStateScore.Finish(scoreRedInitial)
        }
    }

    fun removeScoreRed() = viewModelScope.launch {
        val scoreRedInitial = mutableScoreRed.value.hashCode()

        if (scoreRedInitial > 0) {
            mutableScoreRed.value = UiStateScore.Success(scoreRedInitial - 1)
        } else {
            mutableScoreRed.value = UiStateScore.Error(scoreRedInitial)
        }
    }

    // Change Blue

    fun addScoreBlue() = viewModelScope.launch {
        var scoreBlueInitial = mutableScoreBlue.value.hashCode()

        mutableScoreBlue.value = UiStateScore.Success(++scoreBlueInitial)

        if (checkVictory(scoreBlueInitial, mutableScoreRed.value.hashCode())) {
            mutableScoreBlue.value = UiStateScore.Finish(scoreBlueInitial)
        }
    }

    fun removeScoreBlue() = viewModelScope.launch {
        val scoreBlueInitial = mutableScoreBlue.value.hashCode()

        if (scoreBlueInitial > 0) {
            mutableScoreBlue.value = UiStateScore.Success(scoreBlueInitial - 1)
        } else {
            mutableScoreBlue.value = UiStateScore.Error(scoreBlueInitial)
        }
    }

    // Technical Superiority Change

    fun changeParameters() {
        val scoreRedInitial = scoreRed.value.hashCode()
        val scoreBlueInitial = scoreBlue.value.hashCode()

        mutableTechnicalSuperiority.value = UiStateScore.Success(technicalSuperiorityEditFight.value!!)

        if (checkVictory(scoreRedInitial, scoreBlueInitial)) {
            mutableScoreRed.value = UiStateScore.Finish(scoreRedInitial)
        } else if (checkVictory(scoreBlueInitial, scoreRedInitial)) {
            mutableScoreBlue.value = UiStateScore.Finish(scoreBlueInitial)
        }
    }

    private fun checkVictory(scoreAthleteOne: Int, scoreAthleteTwo: Int): Boolean {
        val technicalSuperiorityInitial = mutableTechnicalSuperiority.value.hashCode()

        return (scoreAthleteOne - scoreAthleteTwo) >= technicalSuperiorityInitial
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
