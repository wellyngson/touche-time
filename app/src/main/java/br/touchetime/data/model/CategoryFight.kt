package br.touchetime.data.model

import android.content.Context
import br.touchetime.R

object CategoryFight {
    fun getListCategory(context: Context): List<String> {
        return listOf(
            context.getString(R.string.category_childish),
            context.getString(R.string.category_u13),
            context.getString(R.string.category_u15),
            context.getString(R.string.category_u17),
            context.getString(R.string.category_u20),
            context.getString(R.string.category_senior)
        )
    }
}
