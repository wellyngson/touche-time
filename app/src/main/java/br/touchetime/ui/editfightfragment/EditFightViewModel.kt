package br.touchetime.ui.editfightfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditFightViewModel : ViewModel() {

    private val mutableTechnicalSuperiority = MutableLiveData(1)
    private val mutableNumberRound = MutableLiveData(1)
    private val mutableTimeRound = MutableLiveData("03:00")
    private val mutableTimeInterval = MutableLiveData("00:30")

    val technicalSuperiority: LiveData<Int> = mutableTechnicalSuperiority
    val numberRound: LiveData<Int> = mutableNumberRound
    val timeRound: LiveData<String> = mutableTimeRound
    val timeInterval: LiveData<String> = mutableTimeInterval

    fun changeTimeInterval(value: Int) {
        val minutes = mutableTimeInterval.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeInterval.value!!.substring(3).toInt()

        mutableTimeInterval.postValue(changeTimer(minutes, seconds, value))
    }

    fun changeTimeRound(value: Int) {
        val minutes = mutableTimeRound.value!!.substring(0, 2).toInt()
        val seconds = mutableTimeRound.value!!.substring(3).toInt()

        mutableTimeRound.postValue(changeTimer(minutes, seconds, value))
    }

    private fun changeTimer(minutes: Int, seconds: Int, value: Int): String {
        return if (minutes == 0 && seconds == 10 && value == -10) {
            stringFormat(seconds, minutes)
        } else {
            var secondsFinal = seconds
            secondsFinal += value

            when {
                secondsFinal > 50 -> {
                    stringFormat(0, minutes + 1)
                }
                secondsFinal < 0 -> {
                    stringFormat(50, minutes - 1)
                }
                else -> {
                    stringFormat(secondsFinal, minutes)
                }
            }
        }
    }

    private fun stringFormat(seconds: Int, minutes: Int): String {
        val secondsFinal = String.format("%02d", seconds)
        val minutesFinal = String.format("%02d", minutes)

        return "$minutesFinal:$secondsFinal"
    }

    fun changeTechnicalSuperiority(value: Int) {
        mutableTechnicalSuperiority.value!!.let {
            if ((it + value) >= 1) {
                mutableTechnicalSuperiority.postValue(it + value)
            }
        }
    }

    fun changeNumberRounds(value: Int) {
        mutableNumberRound.value!!.let {
            if (it + value >= 1) {
                mutableNumberRound.postValue(it + value)
            }
        }
    }
}
