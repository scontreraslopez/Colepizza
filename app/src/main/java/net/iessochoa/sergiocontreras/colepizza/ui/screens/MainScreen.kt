package net.iessochoa.sergiocontreras.colepizza.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
    LazyColumn(
        modifier = modifier
    ) {
        items(daysOfWeek) { day ->
            Button(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { }
            ) {
                Text(text = day)
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() =
    ColepizzaTheme() {
        MainScreen()
    }


