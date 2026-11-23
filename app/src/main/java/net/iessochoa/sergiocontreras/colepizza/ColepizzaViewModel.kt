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

            val clickedDay = currentState.valoraciones.getOrDefault(weekday, emptyMap())

                //currentState.valoraciones.filter { it.key == weekday }.values.first()
            val clickedDayMenuItem = clickedDay.entries.find { it.key == menuItem }

            currentState.copy(


            )

        }

    }



}
