package br.touchetime.ui.chooseathlete

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Athlete
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseAthleteViewModel @Inject constructor() : ViewModel() {

    var athleteRed: Athlete? = null
        private set
    var athleteBlue: Athlete? = null
        private set

    fun setupRedAthlete(athlete: Athlete) {
        this.athleteRed = athleteRed
    }

    fun setupBlueAthlete(athlete: Athlete) {
        this.athleteBlue = athleteRed
    }
}
