package br.touchetime.data.repository

import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import kotlinx.coroutines.flow.StateFlow

interface AthleteRepository {

    val athletes: StateFlow<List<Athlete>>
    val athletesLoadState: StateFlow<NetworkState>

    suspend fun getAthletes()
}
