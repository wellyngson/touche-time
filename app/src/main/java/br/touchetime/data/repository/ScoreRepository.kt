package br.touchetime.data.repository

interface ScoreRepository {
    fun getScoreRed(): Int
    fun getScoreBlue(): Int
}
