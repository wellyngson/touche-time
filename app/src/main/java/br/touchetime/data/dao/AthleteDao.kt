package br.touchetime.data.dao

import androidx.room.Query

interface AthleteDao {

    @Query("SELECT score FROM athlete WHERE id = :id")
    fun getScore(id: Int): Int

    // Need continue
}
