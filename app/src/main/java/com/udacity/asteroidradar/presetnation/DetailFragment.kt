package com.udacity.asteroidradar.presetnation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(
            requireArguments()
        ).selectedAsteroid
        with(binding) {
            this@with.asteroid = asteroid
            if (asteroid.isSaved) {
                ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_24_true)
            } else {
                ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_border_24_false)
            }

            helpButton.setOnClickListener {
                displayAstronomicalUnitExplanationDialog()
            }

            ivFavoritesDetailScreen.setOnClickListener {

                if (asteroid.isSaved) {
                    asteroid.isSaved = false
                    viewModel.updateAsteroidInDatabase(asteroid)
                    ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_border_24_false)


                } else {
                    asteroid.isSaved = true
                    viewModel.updateAsteroidInDatabase(asteroid)
                    ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_24_true)
                }
            }
        }


        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }


}
