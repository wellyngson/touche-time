package br.touchetime.data.repository

import br.touchetime.data.model.ParamsFight
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor() : ScoreRepository {
    private var scoreRed = 0
    private var scoreBlue = 0
    private var technicalSuperiority = 8
    private var numberRounds = 2
    private var timeRound = "03:00"
    private var timeInterval = "00:30"

    override fun getScoreRed(): Int = scoreRed
    override fun addScoreRed(): Int = ++scoreRed
    override fun removeScoreRed(): Int = --scoreRed

    override fun getScoreBlue(): Int = scoreBlue
    override fun addScoreBlue(): Int = ++scoreBlue
    override fun removeScoreBlue(): Int = --scoreBlue

    override fun getTechnicalSuperiority(): Int = technicalSuperiority

    override fun getNumberRounds(): Int = numberRounds

    override fun getTimeRound(): String = timeRound

    override fun getTimeInterval(): String = timeInterval

    override fun changeParams(paramsFight: ParamsFight) {
        paramsFight.let {
            technicalSuperiority = it.technicalSuperiority
            numberRounds = it.numberRounds
            timeRound = it.timeRounds
            timeInterval = it.timeInterval
        }
    }
}
