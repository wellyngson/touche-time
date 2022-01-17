package br.touchetime.data.model

import br.touchetime.R

object StyleFight {

    private val styles = listOf(
        R.string.greco_roman,
        R.string.free_style,
        R.string.woman_wrestling
    )

    fun getListStyle(): List<Int> {
        return styles
    }
}
