package br.touchetime.ui.categoryfragment

import android.content.Context
import androidx.lifecycle.ViewModel
import br.touchetime.data.model.CategoryFight
import br.touchetime.data.model.Fight

class CategoryViewModel : ViewModel() {

    private var fight: Fight? = null

    fun setFight(fight: Fight) {
        this.fight = fight
    }

    fun getFight(): Fight? = fight

    fun setCategorySelected(category: String) {
        this.fight?.category = category
    }

    fun getListCategory(): List<Int> =
        CategoryFight.getListCategory()
}
