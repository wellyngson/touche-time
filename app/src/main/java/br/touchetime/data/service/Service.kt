package br.touchetime.data.service

import br.touchetime.data.model.Athlete
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("touchetime/athletes")
    suspend fun getListAthlete(): List<Athlete>

    @GET("touchetime/athlete/{id}")
    suspend fun getAthleteById(@Path("id") id: Int): Athlete
}
