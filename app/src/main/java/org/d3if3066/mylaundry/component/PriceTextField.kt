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

@Composable
fun PriceTextField(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean,
    value:Double,
    onValueChange:(it:String)->Unit
) {
    var weight by remember { mutableStateOf(0.0) }
    var price by remember { mutableStateOf(0.0) }

        TextField(
            value = (weight * price).toString(),
            onValueChange = {},
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = CustomBlackPurple
                )
            },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

}
