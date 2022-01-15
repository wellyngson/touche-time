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

    fun changeParams(paramsFight: ParamsFight)
}
