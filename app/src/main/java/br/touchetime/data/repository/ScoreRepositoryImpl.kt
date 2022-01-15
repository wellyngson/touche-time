package br.touchetime.data.repository

import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor() : ScoreRepository {
    private var scoreRedInitial = 0
    private var scoreBlueInitial = 0

    override fun getScoreRed(): Int = scoreRedInitial
    override fun addScoreRed(): Int = ++scoreRedInitial
    override fun removeScoreRed(): Int = --scoreRedInitial

    override fun getScoreBlue(): Int = scoreBlueInitial
    override fun addScoreBlue(): Int = ++scoreBlueInitial
    override fun removeScoreBlue(): Int = --scoreBlueInitial
}
