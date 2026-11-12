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
import androidx.compose.ui.draw.alpha
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

        Image(
            painter = painterResource(R.drawable.happy_face),
            contentDescription = null,
            modifier = Modifier
                .selectable(
                    selected = selection == 1,
                    onClick = { onSelection(1) },
                    role = Role.RadioButton
                )
                .clip(CircleShape)

                .border(
                    width = if (selection == 1) 3.dp else 1.dp,
                    color = if (selection == 1) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )

        )

        Image(
            painter = painterResource(R.drawable.neutral_face),
            contentDescription = null,
            modifier = Modifier
                .selectable(
                    selected = selection == 2,
                    onClick = { onSelection(2) },
                    role = Role.RadioButton
                )
                .clip(CircleShape)

                .border(
                    width = if (selection == 2) 3.dp else 1.dp,
                    color = if (selection == 2) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )

        )

        Image(
            painter = painterResource(R.drawable.sad_face),
            contentDescription = null,
            modifier = Modifier
                .selectable(
                    selected = selection == 3,
                    onClick = { onSelection(3) },
                    role = Role.RadioButton
                )
                .clip(CircleShape)
                .border(
                    width = if (selection == 3) 3.dp else 1.dp,
                    color = if (selection == 3) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )

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