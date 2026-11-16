package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.R
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

private val smileys = listOf(
    R.drawable.happy_face to R.string.smiley_happy,
    R.drawable.neutral_face to R.string.smiley_neutral,
    R.drawable.sad_face to R.string.smiley_sad,
)

@Composable
fun SmileyRating(
    selection: Int,
    onSelection: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        smileys.forEachIndexed { index, (smileyRes, descriptionRes) ->
            val option = index + 1
            SmileySelectable(
                smileyRes = smileyRes,
                descriptionRes = descriptionRes,
                selected = selection == option,
                onClick = { onSelection(option) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmileyRatingPreview() {
    ColepizzaTheme {
        SmileyRating(
            selection = 2,
            onSelection = {}
        )
    }
}