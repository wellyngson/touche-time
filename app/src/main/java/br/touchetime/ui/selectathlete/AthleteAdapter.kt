package br.touchetime.ui.selectathlete

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.touchetime.R
import br.touchetime.data.model.Athlete
import br.touchetime.databinding.ItemAthleteBinding
import br.touchetime.extension.inflate

class AthleteAdapter(
    private val athleteHandler: AthleteHandler,
) : ListAdapter<Athlete, AthleteAdapter.AthleteViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteViewHolder =
        AthleteViewHolder(
            parent.inflate(R.layout.item_athlete, false),
            athleteHandler
        )

    override fun onBindViewHolder(holder: AthleteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AthleteViewHolder(
        view: View,
        athleteHandler: AthleteHandler,
    ) : RecyclerView.ViewHolder(view) {

        private var athlete: Athlete? = null
        private val viewBinding = ItemAthleteBinding.bind(itemView)

        init {
            viewBinding.container.setOnClickListener {
                athlete?.let { athleteHandler.onClick(it) }
            }
            viewBinding.container.setOnLongClickListener {
                athlete?.let { athleteHandler.onLongClick(it) }
                true
            }
        }

        fun bind(athlete: Athlete) {
            this.athlete = athlete

            viewBinding.apply {
                name.text = athlete.name
                style.text = athlete.style
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Athlete>() {
            override fun areItemsTheSame(oldItem: Athlete, newItem: Athlete): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Athlete, newItem: Athlete): Boolean =
                oldItem == newItem
        }
    }

    interface AthleteHandler {
        fun onClick(athlete: Athlete)
        fun onLongClick(athlete: Athlete)
    }
}
