package org.d3if3066.mylaundry.component

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            text = { Text(text = "Apakah Anda Yakin ?") },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text(text = stringResource(R.string.hapus))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.kembali))
                }
            },
            onDismissRequest = { onDismissRequest() }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    MyLaundryTheme {
        DisplayAlertDialog(
            openDialog = true,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}