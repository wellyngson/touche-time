package br.touchetime.ui.categoryfragment

import androidx.lifecycle.ViewModel
import br.touchetime.data.model.CategoryFight

class CategoryViewModel : ViewModel() {

    fun getListCategory(): List<Int> = CategoryFight.getListCategory()
}
