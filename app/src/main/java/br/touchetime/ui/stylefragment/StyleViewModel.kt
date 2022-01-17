package br.touchetime.ui.stylefragment

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.StyleFight

class StyleViewModel : ViewModel() {

    fun getListStyle(): List<Int> = StyleFight.getListStyle()
}
