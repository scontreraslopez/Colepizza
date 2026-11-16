package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import net.iessochoa.sergiocontreras.colepizza.R

enum class Rating(
    @DrawableRes val smileyRes: Int,
    @StringRes val descriptionRes: Int
) {
    HAPPY(R.drawable.happy_face, R.string.smiley_happy),
    NEUTRAL(R.drawable.neutral_face, R.string.smiley_neutral),
    SAD(R.drawable.sad_face, R.string.smiley_sad)
}
