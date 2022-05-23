package br.touchetime.data.repository

import br.touchetime.data.model.ParamsFight

interface ScoreRepository {
    fun getScoreRed(): Int
    fun addScoreRed(): Int
    fun removeScoreRed(): Int

    fun getScoreBlue(): Int
    fun addScoreBlue(): Int
    fun removeScoreBlue(): Int

    fun getTechnicalSuperiority(): Int
    fun getNumberRounds(): Int
    fun getTimeRound(): String
    fun getTimeInterval(): String

    fun changeParams(paramsFight: ParamsFight)
}
