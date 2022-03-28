package br.touchetime.data.service

import br.touchetime.data.model.Athlete
import retrofit2.http.GET

interface Service {

    @GET("touchetime/athletes")
    suspend fun getListAthlete(): List<Athlete>
}
