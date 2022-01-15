package br.touchetime.data.repository

interface ScoreRepository {
    fun addScoreRed(): Int
    fun removeScoreRed(): Int

    fun addScoreBlue(): Int
    fun removeScoreBlue(): Int
}
