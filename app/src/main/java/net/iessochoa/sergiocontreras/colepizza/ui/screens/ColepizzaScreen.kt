package net.iessochoa.sergiocontreras.colepizza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.colepizza.model.MenuItem
import net.iessochoa.sergiocontreras.colepizza.ui.components.MenuRow
import net.iessochoa.sergiocontreras.colepizza.model.Rating
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme


@Composable
fun ColepizzaScreen(
    modifier: Modifier = Modifier
) {

    val selectionList: SnapshotStateList<Rating> = remember {
        MenuItem.entries.map { Rating.NEUTRAL }.toMutableStateList()
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        MenuItem.entries.forEachIndexed { index, menuItem ->

            MenuRow(
                item = menuItem,
                selection = selectionList[index],
                onSelection = { selectionList[index] = it }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
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