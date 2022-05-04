package br.touchetime.ui.createathlete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.Athlete
import br.touchetime.data.model.CategoryFight
import br.touchetime.data.repository.AthleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAthleteViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository,
) : ViewModel() {

    fun createAthlete(athlete: Athlete) {
        viewModelScope.launch {
            athleteRepository.createAthlete(athlete)
        }
    }

    fun getAllCategory(): List<Int> = CategoryFight.getListCategory()
}
