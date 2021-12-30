package br.touchetime.ui.scorefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

    private val mutableScoreRed = MutableLiveData(0)
    private val mutableScoreBlue = MutableLiveData(0)
    private val mutableFoulRed = MutableLiveData(0)
    private val mutableFoulBlue = MutableLiveData(0)

    private val mutableTechnicalSuperiority = MutableLiveData(10)

    val scoreRed: LiveData<Int> = mutableScoreRed
    val scoreBlue: LiveData<Int> = mutableScoreBlue
    val foulRed: LiveData<Int> = mutableFoulRed
    val foulBlue: LiveData<Int> = mutableFoulBlue

    val technicalSuperiority: LiveData<Int> = mutableTechnicalSuperiority

    fun addScore(color: String) {
        val scoreFinal: Int

        if (color == "red") {
            scoreFinal = mutableScoreRed.value!! + 1
            mutableScoreRed.postValue(scoreFinal)
        } else {
            scoreFinal = mutableScoreBlue.value!! + 1
            mutableScoreBlue.postValue(scoreFinal)
        }
    }

    fun removeScore(color: String) {
        val scoreFinal: Int
        val scoreInitial: Int

        if (color == "red") {
            scoreInitial = mutableScoreRed.value!!

            if (scoreInitial > 0) {
                scoreFinal = scoreInitial - 1
                mutableScoreRed.postValue(scoreFinal)
            }
        } else {
            scoreInitial = mutableScoreBlue.value!!

            if (scoreInitial > 0) {
                scoreFinal = scoreInitial - 1
                mutableScoreBlue.postValue(scoreFinal)
            }
        }
    }

    fun addFoul(athleteColor: String) {
        if (athleteColor == "red") {
            mutableFoulRed.postValue(mutableFoulRed.value!! + 1)

            if (mutableFoulRed.value!! == 2) {
                addScore(athleteColor)
            }
        } else {
            mutableFoulBlue.postValue(mutableFoulBlue.value!! + 1)

            if (mutableFoulBlue.value!! == 2) {
                addScore(athleteColor)
            }
        }
    }
}
