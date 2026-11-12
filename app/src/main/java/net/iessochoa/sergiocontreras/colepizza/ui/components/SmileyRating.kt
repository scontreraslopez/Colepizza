package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.R
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun SmileyRating (
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(R.drawable.happy_face),
            contentDescription = null
        )

        Image(
            painter = painterResource(R.drawable.neutral_face),
            contentDescription = null
        )

        Image(
            painter = painterResource(R.drawable.sad_face),
            contentDescription = null
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