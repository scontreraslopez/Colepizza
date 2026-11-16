package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import net.iessochoa.sergiocontreras.colepizza.R

enum class MenuItem (
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
) {
    WATER(R.string.menu_water, R.drawable.menu_water),
    STARTER(R.string.menu_starter, R.drawable.menu_starter),
    FIRST_COURSE(R.string.menu_first_course, R.drawable.menu_first_course),
    MAIN_COURSE(R.string.menu_main_course, R.drawable.menu_main_course),
    DESSERT(R.string.menu_dessert, R.drawable.menu_dessert)
}