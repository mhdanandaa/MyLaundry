package org.d3if3066.mylaundry.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.focusedTextFieldText
import org.d3if3066.mylaundry.ui.theme.textFieldContainer
import org.d3if3066.mylaundry.ui.theme.unfocusedTextFieldText

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value:String,
    onValueChange:(it:String)->Unit
) {


    TextField(
        modifier = modifier,
        value = value ,
        onValueChange = {onValueChange(it)},
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = CustomBlackPurple
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
        ),

        )
}