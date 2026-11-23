package net.iessochoa.sergiocontreras.colepizza.ui

import net.iessochoa.sergiocontreras.colepizza.model.MenuItem
import net.iessochoa.sergiocontreras.colepizza.model.Rating
import net.iessochoa.sergiocontreras.colepizza.model.Weekday

data class ColepizzaUiState (
    val valoraciones: Map<Weekday, Map<MenuItem, Rating>>
)