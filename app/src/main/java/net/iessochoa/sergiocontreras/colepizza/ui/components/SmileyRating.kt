package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.colepizza.R
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun SmileyRating (
    modifier: Modifier = Modifier,
    selection: Int,
    onSelection: (Int) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SmileySelectable(
            smileyRes = R.drawable.happy_face,
            option = 1,
            selectedOption = selection,
            onSelection = { onSelection(1) }
        )
        SmileySelectable(
            smileyRes = R.drawable.neutral_face,
            option = 2,
            selectedOption = selection,
            onSelection = { onSelection(2) }
        )
        SmileySelectable(
            smileyRes = R.drawable.sad_face,
            option = 3,
            selectedOption = selection,
            onSelection = { onSelection(3) }
        )



    }
}

@Preview(showBackground = true)
@Composable
fun SmileyRatingPreview() {
    ColepizzaTheme() {
        SmileyRating(
            selection = 2,
            onSelection = {}
        )
    }
}