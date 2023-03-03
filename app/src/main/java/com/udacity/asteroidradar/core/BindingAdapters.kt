package com.udacity.asteroidradar.core

import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("dynamicContentDescriptionForImage")
fun bindContentDescription(imageView: ImageView, description: String?){
    if(!description.isNullOrEmpty()){
        imageView.contentDescription = description
    } else {
        imageView.contentDescription = imageView.context.getString(R.string.image_of_the_day_content_description_empty)
    }

}

@BindingAdapter("isHazardousDynamicContentDescription")
fun bindHazardousContentDescription(imageView: ImageView, isHazardous: Boolean){
    if(isHazardous){
        imageView.contentDescription = imageView.context.getString(R.string.asteroids_hazard_cont_descr_true)
    } else {
        imageView.contentDescription = imageView.context.getString(R.string.asteroids_hazard_cont_descr_false)
    }
}


@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(imageView: ImageView, url: String?) {
    url?.let {
        Picasso
            .with(imageView.context)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .into(imageView)
    }
}

