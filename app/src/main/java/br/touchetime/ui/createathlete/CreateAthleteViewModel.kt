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

    var style: Int? = null
        private set
    var category: Int? = null
        private set
    var weight: Int? = null
        private set

    fun setStyle(style: Int) {
        this.style = style
    }

    fun setCategory(category: Int) {
        this.category = category
    }

    fun setWeight(weight: Int) {
        this.weight = weight
    }

    fun createAthlete(athlete: Athlete) {
        viewModelScope.launch {
            athleteRepository.createAthlete(athlete)
        }
    }

    fun getAllCategory(): List<Int> = CategoryFight.getListCategory()
}
