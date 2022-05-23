package br.touchetime.data.repository

import androidx.lifecycle.LiveData
import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import kotlinx.coroutines.flow.StateFlow

interface AthleteRepository {

    val athlete: LiveData<Athlete>
    val athletes: StateFlow<List<Athlete>>

    val athleteLoadState: StateFlow<NetworkState>
    val athletesLoadState: StateFlow<NetworkState>

    suspend fun getAthletes()
    suspend fun getAthletesById(id: Int)
    suspend fun deleteAthleteById(id: Int)
    suspend fun updateAthlete(athlete: Athlete)
    suspend fun createAthlete(athlete: Athlete)
}
