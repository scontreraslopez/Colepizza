package net.iessochoa.sergiocontreras.colepizza

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.ui.ColepizzaScreen
import net.iessochoa.sergiocontreras.colepizza.ui.screens.MainScreen
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

/**
 * Project: Colepizza
 * From: net.iessochoa.sergiocontreras.colepizza
 * Created by: Contr
 * On: 12/11/2026 at 21:25
 * Creado en Settings -> Editor -> File and Code Templates
 */

@Composable
fun ColePizzaApp () {
    ColepizzaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
           //MainScreen(
            ColepizzaScreen(
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() =
    ColepizzaTheme {
        ColePizzaApp()

    }
