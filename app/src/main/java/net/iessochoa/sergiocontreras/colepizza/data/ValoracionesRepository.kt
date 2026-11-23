package net.iessochoa.sergiocontreras.colepizza.data

import net.iessochoa.sergiocontreras.colepizza.model.MenuItem
import net.iessochoa.sergiocontreras.colepizza.model.Rating
import net.iessochoa.sergiocontreras.colepizza.model.Weekday

/**
 * Datos de ejemplo escritos a mano: la valoración de cada plato para cada día de la semana.
 */
object ValoracionesRepository {

    val valoraciones = getValoraciones()
    fun getValoraciones(): Map<Weekday, Map<MenuItem, Rating>> = mapOf(
        Weekday.LUNES to fillDia(
            water = Rating.HAPPY,
            starter = Rating.NEUTRAL,
            firstCourse = Rating.HAPPY,
            mainCourse = Rating.SAD,
            dessert = Rating.HAPPY
        ),
        Weekday.MARTES to fillDia(
            water = Rating.HAPPY,
            starter = Rating.SAD,
            firstCourse = Rating.NEUTRAL,
            mainCourse = Rating.NEUTRAL,
            dessert = Rating.HAPPY
        ),
        Weekday.MIERCOLES to fillDia(
            water = Rating.HAPPY,
            starter = Rating.HAPPY,
            firstCourse = Rating.SAD,
            mainCourse = Rating.HAPPY,
            dessert = Rating.NEUTRAL
        ),
        Weekday.JUEVES to fillDia(
            water = Rating.NEUTRAL,
            starter = Rating.NEUTRAL,
            firstCourse = Rating.HAPPY,
            mainCourse = Rating.SAD,
            dessert = Rating.SAD
        ),
        Weekday.VIERNES to fillDia(
            water = Rating.HAPPY,
            starter = Rating.HAPPY,
            firstCourse = Rating.HAPPY,
            mainCourse = Rating.NEUTRAL,
            dessert = Rating.HAPPY
        )
    )

    fun getValoracionDia(dia: Weekday): Map<MenuItem, Rating> {
        return valoraciones[dia] ?: emptyMap()
    }

    private fun fillDia(
        water: Rating,
        starter: Rating,
        firstCourse: Rating,
        mainCourse: Rating,
        dessert: Rating
    ): Map<MenuItem, Rating> = mapOf(
        MenuItem.WATER to water,
        MenuItem.STARTER to starter,
        MenuItem.FIRST_COURSE to firstCourse,
        MenuItem.MAIN_COURSE to mainCourse,
        MenuItem.DESSERT to dessert
    )
}
