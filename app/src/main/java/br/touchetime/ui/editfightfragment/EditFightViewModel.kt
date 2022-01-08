package br.touchetime.ui.editfightfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.touchetime.extension.changeTimer

class EditFightViewModel : ViewModel() {

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
