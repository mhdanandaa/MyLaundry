package org.d3if3066.mylaundry.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import java.text.DecimalFormat

@Composable
fun PriceTextField(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean,
    value: Double,
    onValueChange: (it: String) -> Unit,
    supportingText:  @Composable() (() -> Unit)?
) {
    val dec = DecimalFormat("#,###.##")
    TextField(
        supportingText = supportingText,
        value = dec.format(value),
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
//                    color = CustomBlackPurple
            )
        },
        prefix = {
            Text(text = "Rp.")
        },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),

    )

}
