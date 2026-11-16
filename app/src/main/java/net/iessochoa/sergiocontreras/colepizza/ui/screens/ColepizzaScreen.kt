package net.iessochoa.sergiocontreras.colepizza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.ui.components.MenuRow
import net.iessochoa.sergiocontreras.colepizza.ui.components.Rating
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

private val fullMenu = listOf(
    "AGUA", "ENTRANTE", "PRIMER PLATO", "SEGUNDO PLATO", "POSTRE"
)

@Composable
fun ColepizzaScreen(
    modifier: Modifier = Modifier
) {
    val selectionList: SnapshotStateList<Rating> = remember {
        fullMenu.map { Rating.NEUTRAL }.toMutableStateList()
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        fullMenu.forEachIndexed { index, menuItem ->

            MenuRow(
                item = menuItem,
                selection = selectionList[index],
                onSelection = { selectionList[index] = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColepizzaScreenPreview() {
    ColepizzaTheme {
        ColepizzaScreen()
    }
}