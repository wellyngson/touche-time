package br.touchetime.ui.createfight

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Fight

class CreateFightViewModel : ViewModel() {

    private var fight: Fight? = Fight(
        "U15",
        "Greco-Roman",
        58
    )

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight

    fun setCategory(category: String) {
        fight?.category = category
    }

    fun setStyle(style: String) {
        fight?.style = style
    }

    fun setWeight(weight: Int) {
        fight?.weight = weight
    }
}
