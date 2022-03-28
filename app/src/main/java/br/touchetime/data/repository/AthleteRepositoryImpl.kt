package br.touchetime.data.repository

import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import br.touchetime.data.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AthleteRepositoryImpl @Inject constructor(
    private val service: Service
) : AthleteRepository {

    private val _athletes = MutableStateFlow<List<Athlete>>(emptyList())
    private val _athletesLoadState = MutableStateFlow<NetworkState>(NetworkState.Idle)

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
}
