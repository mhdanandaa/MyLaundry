package org.d3if3066.mylaundry.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.focusedTextFieldText
import org.d3if3066.mylaundry.ui.theme.textFieldContainer
import org.d3if3066.mylaundry.ui.theme.unfocusedTextFieldText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipeDropDown(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value:String,
    onValueChange:(it:String)->Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var tipe by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = tipe,
                onValueChange = { tipe = it },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = CustomBlackPurple
                    )
                },
                readOnly = true,
                modifier = Modifier.menuAnchor().then(modifier),
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                    unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Cuci Kering")},
                    onClick = {
                        tipe = "Cuci Kering"
                        expanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Cuci setrika")},
                    onClick = {
                        tipe = "Cuci setrika"
                        expanded = false
                    })

            }
        }
    }
}