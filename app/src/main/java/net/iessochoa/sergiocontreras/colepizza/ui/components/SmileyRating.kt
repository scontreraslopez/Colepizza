package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.R
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun SmileyRating (
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        Image(
            painter = painterResource(R.drawable.happy_face),
            contentDescription = "Selector de cara feliz",
            modifier = Modifier.selectable(
                selected = false,
                onClick = { },
                role = Role.RadioButton
            )
        )

        Image(
            painter = painterResource(R.drawable.neutral_face),
            contentDescription = "Selector de cara neutra",
            modifier = Modifier.selectable(
                selected = false,
                onClick = { },
                role = Role.RadioButton
            )
        )

        Image(
            painter = painterResource(R.drawable.sad_face),
            contentDescription = "Selector de cara triste",
            modifier = Modifier.selectable(
                selected = false,
                onClick = { },
                role = Role.RadioButton
            )
        )

    }
}

@Preview
@Composable
fun SmileyRatingPreview() {
    ColepizzaTheme() {
        SmileyRating()
    }
}