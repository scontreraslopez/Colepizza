package net.iessochoa.sergiocontreras.colepizza

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.sergiocontreras.colepizza.ui.screens.ColepizzaScreen
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

/**
 * Project: Colepizza
 * From: net.iessochoa.sergiocontreras.colepizza
 * Created by: Contr
 * On: 12/11/2026 at 21:25
 * Creado en Settings -> Editor -> File and Code Templates
 */

@Composable
fun ColepizzaApp (
    viewModel: ColepizzaViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ColepizzaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColepizzaScreen(
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}


