package org.d3if3066.mylaundry.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
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
import org.d3if3066.mylaundry.model.Service
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.focusedTextFieldText
import org.d3if3066.mylaundry.ui.theme.textFieldContainer
import org.d3if3066.mylaundry.ui.theme.unfocusedTextFieldText
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipeDropDown(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (it: String) -> Unit,
    serviceList: List<Service>
) {
    var expanded by remember { mutableStateOf(false) }
    serviceList.forEachIndexed { index, it ->
        if (index == 0 && value == "") {
            onValueChange(it.name)
            return@forEachIndexed
        } else return@forEachIndexed
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = CustomBlackPurple
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .then(modifier),
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                    unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon", tint = CustomBlackPurple)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                serviceList.forEachIndexed { index, it ->
                    DropdownMenuItem(
                        text = { Text(text = it.name + " ( Rp. "+ DecimalFormat("#,###.##").format(it.price)+" / Kg )") },
                        onClick = {
                            onValueChange(it.name)
                            expanded = false
                        })
                }


            }
        }
    }
}