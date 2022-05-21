package br.touchetime.ui.chooseathlete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.Athlete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAthleteViewModel @Inject constructor() : ViewModel() {

    private val _athleteRed = MutableLiveData<Athlete>()
    private val _athleteBlue = MutableLiveData<Athlete>()

    val athleteRed: LiveData<Athlete> = _athleteRed
    val athleteBlue: LiveData<Athlete> = _athleteBlue

    var athleteSelected: String? = null
        private set

    fun setupRedAthlete(athlete: Athlete) {
        viewModelScope.launch {
            _athleteRed.postValue(athlete)
        }
    }

    fun setupBlueAthlete(athlete: Athlete) {
        viewModelScope.launch {
            _athleteBlue.postValue(athlete)
        }
    }

    fun setupAthleteSelected(const: String) {
        this.athleteSelected = const
    }
}
