package net.iessochoa.sergiocontreras.colepizza.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.colepizza.R

@Composable
fun SmileySelectable(
    @DrawableRes smileyRes: Int,
    @StringRes descriptionRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(smileyRes),
        contentDescription = stringResource(descriptionRes),
        modifier = modifier
            .clip(CircleShape)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SmileySelectablePreview() {
    SmileySelectable(
        smileyRes = R.drawable.sad_face,
        descriptionRes = R.string.smiley_sad,
        selected = true,
        onClick = {}
    )
}