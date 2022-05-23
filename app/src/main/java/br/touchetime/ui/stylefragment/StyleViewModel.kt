package br.touchetime.ui.stylefragment

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.Fight
import br.touchetime.data.model.StyleFight

class StyleViewModel : ViewModel() {

    private var fight: Fight? = null

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight

    fun setStyleSelected(style: String) {
        this.fight?.style = style
    }

    fun getListStyle(): List<Int> = StyleFight.getListStyle()
}
