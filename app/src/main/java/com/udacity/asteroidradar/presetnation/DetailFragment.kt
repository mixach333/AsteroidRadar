package com.udacity.asteroidradar.presetnation


import android.animation.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
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
                ivFavoritesDetailScreen.apply{
                    setImageResource(R.drawable.ic_baseline_star_24_true)
                    scaleX = 1.6f
                    scaleY = 1.6f
                }
            } else {
                ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_border_24_false)
            }

            helpButton.setOnClickListener {
                displayAstronomicalUnitExplanationDialog()
            }

            ivFavoritesDetailScreen.setOnClickListener { starView ->
                val animatorSetPair = getTrueAndFalseAnimatorSetsForFavoritesButton(starView)
                val animatorSetTrue = animatorSetPair.first
                val animatorSetFalse = animatorSetPair.second

                if (asteroid.isSaved) {
                    asteroid.isSaved = false
                    viewModel.updateAsteroidInDatabase(asteroid)
                    ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_border_24_false)
                    animatorSetFalse.start()

                } else {
                    asteroid.isSaved = true
                    viewModel.updateAsteroidInDatabase(asteroid)
                    ivFavoritesDetailScreen.setImageResource(R.drawable.ic_baseline_star_24_true)
                    animatorSetTrue.start()
                    repeat(25) {
                        shower(starView)
                    }
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

    private fun getTrueAndFalseAnimatorSetsForFavoritesButton(view: View): Pair<AnimatorSet, AnimatorSet> {
        val scaleXUp = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.6f)
        val scaleYUp = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.6f)
        val scaleXDown = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
        val scaleYDown = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)

        val animatorRotate = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
        val animatorScaleUp = ObjectAnimator.ofPropertyValuesHolder(view, scaleXUp, scaleYUp)
        val animatorScaleDown = ObjectAnimator.ofPropertyValuesHolder(view, scaleXDown, scaleYDown)

        val animatorSetTrue = AnimatorSet()
        animatorSetTrue.play(animatorRotate).with(animatorScaleUp)
        animatorSetTrue.duration = 300L

        val animatorSetFalse = AnimatorSet()
        animatorSetFalse.play(animatorRotate).with(animatorScaleDown)
        animatorSetFalse.duration = 300L

        animatorSetTrue.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })

        animatorSetFalse.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
        return Pair(animatorSetTrue, animatorSetFalse)
    }

    private fun shower(star: View) {
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        val newStar = AppCompatImageView(this.requireContext())
        newStar.setImageResource(R.drawable.ic_baseline_star_24_true)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        val rotator = ObjectAnimator.ofFloat(
            newStar, View.ROTATION,
            (Math.random() * 1080).toFloat()
        )
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })

        set.start()
    }

}
