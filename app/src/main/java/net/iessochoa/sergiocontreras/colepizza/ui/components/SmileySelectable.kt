package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.selection.selectable
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

@Composable
fun SmileySelectable(
    @DrawableRes smileyRes: Int,
    option: Int,
    selectedOption: Int,
    onSelection: (Int) -> Unit
) {
    val isSelected = selectedOption == option
    Image(
        painter = painterResource(smileyRes),
        contentDescription = null,
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = { onSelection(option) },
                role = Role.RadioButton
            )
            .clip(CircleShape)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SmileySelectablePreview() {
    SmileySelectable(
        smileyRes = R.drawable.sad_face,
        option = 3,
        selectedOption = 3,
        onSelection = {}
    )
}