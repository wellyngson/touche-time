package br.touchetime.data.repository

interface ScoreRepository {
    fun getScoreRed(): Int
    fun addScoreRed(): Int
    fun removeScoreRed(): Int

    fun getScoreBlue(): Int
    fun addScoreBlue(): Int
    fun removeScoreBlue(): Int
}
