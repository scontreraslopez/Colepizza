package net.iessochoa.sergiocontreras.colepizza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.ui.components.MenuRow

@Composable
fun ColepizzaScreen() {

    val fullMenu = listOf(
        "ENTRANTE","PRIMERO", "SEGUNDO", "POSTRE"
    )
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        fullMenu.forEach{
            MenuRow(it)
        }

    }

}

@Preview (showBackground = true)
@Composable
fun ColepizzaScreenPreview()= ColepizzaScreen()