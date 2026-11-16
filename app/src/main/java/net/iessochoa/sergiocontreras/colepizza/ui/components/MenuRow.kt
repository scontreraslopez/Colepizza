package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.R
import net.iessochoa.sergiocontreras.colepizza.ui.theme.ColepizzaTheme

@Composable
fun MenuRow(
    item: MenuItem,
    selection: Rating,
    onSelection: (Rating) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemName = stringResource(item.labelRes)
    val itemImage = painterResource(item.iconRes)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = itemImage,
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = itemName,
            modifier = Modifier.weight(1f)
        )
        SmileyRating(
            modifier = Modifier.weight(2f),
            selection = selection,
            onSelection = onSelection

        )
    }
}

@Preview (showBackground = true)
@Composable
fun MenuRowPreview() =
    ColepizzaTheme{
        MenuRow(
            item = MenuItem.entries.first(),
            selection = Rating.NEUTRAL,
            onSelection = {}
        )
    }