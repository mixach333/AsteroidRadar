package com.udacity.asteroidradar.presetnation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidItemAdapter(val clickListener: (selectedAsteroid: Asteroid) -> Unit) :
    ListAdapter<Asteroid, AsteroidItemAdapter.AsteroidItemViewHolder>(AsteroidItemDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return AsteroidItemViewHolder(
            AsteroidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val currentAsteroid = getItem(position)
        with(holder.binding) {
            tvName.text = currentAsteroid.codename
            tvDate.text = currentAsteroid.closeApproachDate
            ivIsHazardous.setImageResource(
                when (currentAsteroid.isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    false -> R.drawable.ic_status_normal
                }
            )
            root.setOnClickListener { clickListener(currentAsteroid) }
        }
    }

    class AsteroidItemViewHolder(val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}


class AsteroidItemDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}