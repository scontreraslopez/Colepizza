package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun SmileyRating(
    selection: Rating,
    onSelection: (Rating) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Rating.entries.forEach { rating ->
            SmileySelectable(
                smileyRes = rating.smileyRes,
                descriptionRes = rating.descriptionRes,
                selected = selection == rating,
                onClick = { onSelection(rating) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmileyRatingPreview() {
    ColepizzaTheme {
        SmileyRating(
            selection = Rating.NEUTRAL,
            onSelection = {}
        )
    }
}