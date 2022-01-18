package br.touchetime.ui.categoryfragment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.touchetime.R
import br.touchetime.data.model.Category
import br.touchetime.databinding.CategoryItemBinding
import br.touchetime.extension.inflate
import br.touchetime.utils.CategoryHandler

class CategoryAdapter(
    private val listCategory: List<String>,
    private val categoryHandler: CategoryHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.category_item), categoryHandler::onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(listCategory[position])
        }
    }

    override fun getItemCount(): Int = listCategory.size

    class ViewHolder(
        view: View,
        onClickListener: (Category) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = CategoryItemBinding.bind(view)
        private var categoryViewHolder: Category? = null

        init {
            view.setOnClickListener {
                categoryViewHolder?.let { onClickListener }
            }
        }

        fun bind(category: String) {
            viewBinding.apply {
                categoryViewHolder?.category = category
                text.text = categoryViewHolder?.category
            }
        }
    }
}
