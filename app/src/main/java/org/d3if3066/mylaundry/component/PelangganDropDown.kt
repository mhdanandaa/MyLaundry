package org.d3if3066.mylaundry.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3066.mylaundry.model.Customer
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.ui.theme.focusedTextFieldText
import org.d3if3066.mylaundry.ui.theme.textFieldContainer
import org.d3if3066.mylaundry.ui.theme.unfocusedTextFieldText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PelangganDropDown(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (it: String) -> Unit,
    customerList: List<Customer>
) {
    var expanded by remember { mutableStateOf(false) }
    val customerListString = customerList.map { customer: Customer -> customer.name }
    var matchedCustomer by remember {
        mutableStateOf(customerListString)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = if (matchedCustomer.isNotEmpty()) expanded else false,
            onExpandedChange = {
                if (matchedCustomer.isNotEmpty())
                 expanded = it
                else expanded = false
            }
        ) {
            TextField(
                value = value,
                onValueChange = {

                    matchedCustomer = cariKataDalamList(it, customerListString)
                    if (matchedCustomer.isNotEmpty()) expanded = true
                    else expanded = false
                    onValueChange(it)
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = CustomBlackPurple
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                  imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
//                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .then(modifier),
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
                matchedCustomer.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            onValueChange(it)
                            expanded = false
                        })
                }


            }
        }
    }
}

fun cariKataDalamList(kataDicari: String, daftarKata: List<String>): List<String> {
    if (kataDicari == "") return emptyList()
    val regex = Regex("\\b.*$kataDicari.*\\b", RegexOption.IGNORE_CASE)
    return daftarKata.filter { regex.containsMatchIn(it) }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyLaundryTheme {
        PelangganDropDown(
            label = "Label", // Provide a label for the dropdown
            trailing = "Trailing", // Provide trailing text/icon for the dropdown
            value = "Selected Value", // Provide the current selected value
            onValueChange = { },
            customerList = emptyList()
        )
    }
}