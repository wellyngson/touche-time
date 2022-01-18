package br.touchetime.ui.weightfragment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.touchetime.R
import br.touchetime.databinding.CategoryItemBinding
import br.touchetime.extension.inflate

class WeightAdapter(
    private val listWeight: List<Int>,
    private val onItemClicked: (position: Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.category_item), onItemClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(listWeight[position])
        }
    }

    override fun getItemCount(): Int = listWeight.size

    class ViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = CategoryItemBinding.bind(view)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
                it.isSelected = !it.isSelected
            }
        }

        fun bind(style: Int) {
            viewBinding.apply {
                text.setText(style)
            }
        }
    }
}