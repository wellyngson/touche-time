package br.touchetime.ui.selectathlete

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import br.touchetime.data.repository.AthleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectAthleteViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository,
) : ViewModel() {

    var athleteSelected: String? = null
        private set
    val athletes: LiveData<List<Athlete>>
        get() = athleteRepository.athletes.asLiveData()
    val athletesLoadState: LiveData<NetworkState>
        get() = athleteRepository.athletesLoadState.asLiveData()

    init {
        getAthletes()
    }

    fun setupAthleteSelected(athleteSelected: String) {
        this.athleteSelected = athleteSelected
    }

    private fun getAthletes() {
        viewModelScope.launch {
            athleteRepository.getAthletes()
        }
    }
}
