package br.touchetime.ui.weightfragment

import android.content.Context
import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Fight
import br.touchetime.data.model.WeightFight

class WeightViewModel : ViewModel() {

    private var fight: Fight? = null

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight

    fun setWeightSelected(weight: Int) {
        this.fight?.weight = weight
    }

    fun getListWeight(context: Context): List<Int>? = fight?.let { fight ->
        WeightFight.getListWeight(fight, context)
    }
}
