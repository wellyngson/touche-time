package br.touchetime.data.repository

import br.touchetime.data.model.ParamsFight
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor() : ScoreRepository {
    private var scoreRedInitial = 0
    private var scoreBlueInitial = 0
    private var technicalSuperiority = 8

    override fun getScoreRed(): Int = scoreRedInitial
    override fun addScoreRed(): Int = ++scoreRedInitial
    override fun removeScoreRed(): Int = --scoreRedInitial

    override fun getScoreBlue(): Int = scoreBlueInitial
    override fun addScoreBlue(): Int = ++scoreBlueInitial
    override fun removeScoreBlue(): Int = --scoreBlueInitial

    override fun getTechnicalSuperiority(): Int = technicalSuperiority

    override fun changeParams(paramsFight: ParamsFight) {
    }
}
