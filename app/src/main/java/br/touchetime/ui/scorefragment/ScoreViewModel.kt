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

        // Check if scoreRed is big that scoreBlue and change your state
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

        // Check if scoreBlue is big that scoreBlue and change your state
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

//    private val mutableScoreRed = MutableLiveData(0)
//    private val mutableScoreBlue = MutableLiveData(0)
//    private val mutableFoulRed = MutableLiveData(0)
//    private val mutableFoulBlue = MutableLiveData(0)
//
//    private val mutableTechnicalSuperiority = MutableLiveData(10)
//
//    val scoreRed: LiveData<Int> = mutableScoreRed
//    val scoreBlue: LiveData<Int> = mutableScoreBlue
//    val foulRed: LiveData<Int> = mutableFoulRed
//    val foulBlue: LiveData<Int> = mutableFoulBlue
//
//    val technicalSuperiority: LiveData<Int> = mutableTechnicalSuperiority
//
//    fun addScore(color: String) {
//        val scoreFinal: Int
//
//        if (color == "red") {
//            scoreFinal = mutableScoreRed.value!! + 1
//            mutableScoreRed.postValue(scoreFinal)
//        } else {
//            scoreFinal = mutableScoreBlue.value!! + 1
//            mutableScoreBlue.postValue(scoreFinal)
//        }
//    }
//
//    fun removeScore(color: String) {
//        val scoreFinal: Int
//        val scoreInitial: Int
//
//        if (color == "red") {
//            scoreInitial = mutableScoreRed.value!!
//
//            if (scoreInitial > 0) {
//                scoreFinal = scoreInitial - 1
//                mutableScoreRed.postValue(scoreFinal)
//            }
//        } else {
//            scoreInitial = mutableScoreBlue.value!!
//
//            if (scoreInitial > 0) {
//                scoreFinal = scoreInitial - 1
//                mutableScoreBlue.postValue(scoreFinal)
//            }
//        }
//    }
//
//    fun addFoul(athleteColor: String) {
//        if (athleteColor == "red") {
//            mutableFoulRed.postValue(mutableFoulRed.value!! + 1)
//
//            if (mutableFoulRed.value!! == 2) {
//                addScore(athleteColor)
//            }
//        } else {
//            mutableFoulBlue.postValue(mutableFoulBlue.value!! + 1)
//
//            if (mutableFoulBlue.value!! == 2) {
//                addScore(athleteColor)
//            }
//        }
//    }
}
