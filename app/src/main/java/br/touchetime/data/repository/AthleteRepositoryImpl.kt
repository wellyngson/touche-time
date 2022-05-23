package br.touchetime.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import br.touchetime.data.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AthleteRepositoryImpl @Inject constructor(
    private val service: Service,
) : AthleteRepository {

    private val _athlete = MutableLiveData<Athlete>()
    private val _athleteLoadState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    private val _athletes = MutableStateFlow<List<Athlete>>(emptyList())
    private val _athletesLoadState = MutableStateFlow<NetworkState>(NetworkState.Idle)

    override val athlete: LiveData<Athlete> = _athlete
    override val athleteLoadState: StateFlow<NetworkState> = _athleteLoadState
    override val athletes: StateFlow<List<Athlete>> = _athletes
    override val athletesLoadState: StateFlow<NetworkState> = _athletesLoadState

    override suspend fun getAthletes() {
        withContext(Dispatchers.IO) {
            try {
                _athletesLoadState.value = NetworkState.Loading

                _athletes.value = service.getListAthlete()

                _athletesLoadState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _athletesLoadState.value = NetworkState.Failed(e)
            }
        }
    }

    override suspend fun getAthletesById(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                _athleteLoadState.value = NetworkState.Loading

                _athlete.postValue(service.getAthleteById(id))

                _athleteLoadState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _athleteLoadState.value = NetworkState.Failed(e)
            }
        }
    }

    override suspend fun deleteAthleteById(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                service.deleteAthleteById(id)

                refreshAthletes()
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun updateAthlete(athlete: Athlete) {
        withContext(Dispatchers.IO) {
            try {
                service.updateAthlete(athlete)

                refreshAthletes()
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun createAthlete(athlete: Athlete) {
        withContext(Dispatchers.IO) {
            try {
                service.createAthlete(athlete)

                refreshAthletes()
            } catch (e: Exception) {
            }
        }
    }

    suspend fun refreshAthletes() {
        withContext(Dispatchers.IO) {
            try {
                _athletesLoadState.value = NetworkState.Loading

                val listAthlete = service.getListAthlete()

                _athletesLoadState.value = NetworkState.Loaded

                _athletes.value = listAthlete
            } catch (e: Exception) {
            }
        }
    }
}
