package br.touchetime.ui.createfight

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Fight

class CreateFightViewModel : ViewModel() {

    private var fight: Fight? = null

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight
}
