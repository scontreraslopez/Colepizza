package net.iessochoa.sergiocontreras.colepizza

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.iessochoa.sergiocontreras.colepizza.data.ValoracionesRepository
import net.iessochoa.sergiocontreras.colepizza.model.MenuItem
import net.iessochoa.sergiocontreras.colepizza.model.Rating
import net.iessochoa.sergiocontreras.colepizza.model.Weekday
import net.iessochoa.sergiocontreras.colepizza.ui.ColepizzaUiState

class ColepizzaViewModel : ViewModel() {

    private val initialState = ValoracionesRepository.getValoraciones()
    private val _uiState = MutableStateFlow(ColepizzaUiState(
        valoraciones = initialState
    ))


    val uiState: StateFlow<ColepizzaUiState> = _uiState.asStateFlow()

    fun onSelectionClick(
        weekday: Weekday,
        menuItem: MenuItem,
        rating: Rating
    ) {
        _uiState.update { currentState ->

            // Estaba torpe en clase es un .get no un filter, que para eso tenemos un map. El compilador lo reescribe a [] syntax.
            val clickedDay = currentState.valoraciones[weekday]?.toMutableMap()!!  // Uff me da pánico el !! pero no debería petar
            clickedDay[menuItem] = rating

            currentState.copy(
                valoraciones = currentState.valoraciones + (weekday to clickedDay)
            )

        }

    }



}
