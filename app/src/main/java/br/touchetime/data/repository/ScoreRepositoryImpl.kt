package br.touchetime.data.repository

import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor() : ScoreRepository {
    override fun getScoreRed(): Int = 10

    override fun getScoreBlue(): Int = 10
}
