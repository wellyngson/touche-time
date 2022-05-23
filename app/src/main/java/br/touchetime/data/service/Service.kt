package br.touchetime.data.service

import br.touchetime.data.model.Athlete
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface Service {

    @GET("touchetime/athletes")
    suspend fun getListAthlete(): List<Athlete>

    @GET("touchetime/athlete/{id}")
    suspend fun getAthleteById(@Path("id") id: Int): Athlete

    @DELETE("touchetime/athlete/{id}")
    suspend fun deleteAthleteById(@Path("id") id: Int)

    // Missing test
    @PUT("touchetime/athlete")
    suspend fun updateAthlete(@Body athlete: Athlete)

    // Missing test
    @POST("touchetime/athlete")
    suspend fun createAthlete(@Body athlete: Athlete)
}
