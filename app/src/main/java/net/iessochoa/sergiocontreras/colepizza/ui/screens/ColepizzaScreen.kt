package net.iessochoa.sergiocontreras.colepizza.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.ui.components.MenuRow

@Composable
fun ColepizzaScreen(
    modifier: Modifier = Modifier
) {

    val selectionList: SnapshotStateList<Int> = remember { mutableStateListOf()}

    val fullMenu = listOf(
        "AGUA","ENTRANTE","PRIMER PLATO", "SEGUNDO PLATO", "POSTRE"
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        fullMenu.forEachIndexed { index, menuItem ->

            selectionList.add(2)

            MenuRow(
                item = menuItem,
                selection = selectionList[index],
                onSelection = {selectionList[index] = it}
            )
        }

    }

}

@Preview (showBackground = true)
@Composable
fun ColepizzaScreenPreview()= ColepizzaScreen()