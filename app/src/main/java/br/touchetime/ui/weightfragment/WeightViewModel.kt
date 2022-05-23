package br.touchetime.ui.weightfragment

import android.content.Context
import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Fight
import br.touchetime.data.model.WeightFight

class WeightViewModel : ViewModel() {

    private var fight: Fight? = null
    var style: Int? = null
        private set
    var category: Int? = null
        private set

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight

    fun setWeightSelected(weight: Int) {
        this.fight?.weight = weight
    }

    fun getListWeight(context: Context): List<Int> =
        WeightFight.getListWeight(fight, context)

    fun getListWeightSelected(): List<Int>? =
        this.style?.let { style ->
            this.category?.let { category ->
                WeightFight.getListWeightSelected(style, category)
            }
        }

    fun setStyle(style: Int) {
        this.style = style
    }

    fun setCategory(category: Int) {
        this.category = category
    }
}
