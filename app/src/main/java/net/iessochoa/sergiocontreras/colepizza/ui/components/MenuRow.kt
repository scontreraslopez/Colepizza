package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.sergiocontreras.colepizza.R

@Composable
fun MenuRow(
    item: String,
    selection: Int,
    onSelection: (Int) -> Unit,
    modifier: Modifier = Modifier
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Text(item,
            modifier=Modifier.weight(1f)
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
fun MenuRowPreview() = MenuRow("ENTRANTE", 2, {})
