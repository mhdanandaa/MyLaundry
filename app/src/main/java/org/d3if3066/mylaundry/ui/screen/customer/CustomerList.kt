package org.d3if3066.mylaundry.ui.screen.customer

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.component.DisplayAlertDialog
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.Customer
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerListScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.daftar_pelanggan)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = CustomPurple,
                    //Untuk Judul
                    titleContentColor = CustomWhite
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCustomer.route)
                },
                containerColor = CustomPurple
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_pelanggan),
                    tint = CustomWhite
                )
            }
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))

    }

}

@Composable
fun ScreenContent(modifier: Modifier) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        orderDao = db.orderDao,
        serviceDao = db.serviceDao,
        customerDao = db.customerDao
    )
    val viewModel: CustomerViewModel = viewModel(factory = factory)
    val data by viewModel.customerList.collectAsState()
    var showDeletedCustomerDialog by remember { mutableStateOf(false) }
    var willDeletedCustomer by remember { mutableLongStateOf(0L) }

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong_pelanggan))
        }

    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) { it ->
                ListItems(
                    context,
                    customer = it,
                    changeShowDeletedCustomerDialog = { showDeletedCustomerDialog = it },
                    changeWillDeletedCustomer = { willDeletedCustomer = it },
                    onClick = {}
                )
                Divider()
            }
        }
        DisplayAlertDialog(
            openDialog = showDeletedCustomerDialog,
            onDismissRequest = { showDeletedCustomerDialog = false }
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteCustomer(willDeletedCustomer)
            }
            showDeletedCustomerDialog = false
        }
    }
}

@Composable
fun ListItems(
    context: Context, customer: Customer, onClick: () -> Unit,
    changeShowDeletedCustomerDialog: (it: Boolean) -> Unit,
    changeWillDeletedCustomer: (it: Long) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
        Column {
            Text(
                text = if(customer.name == "") "Anonymous" else customer.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = if (customer.phone.toString() == "") "Nomor Telepon Belum terdaftar" else customer.phone.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(customer.phone != null && customer.phone != "") {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = CustomPurple),
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                    "https://api.whatsapp.com/send?phone=" + changePhoneFormat(
                                        customer.phone.toString()
                                    ) + "&text=Halo"
                                )
                            )
                        )
                    }) {
                    Text(text = "Chat Whatsapp")
                }
            }
            OutlinedButton(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Red,
                ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Red,
                    containerColor = Color.Transparent
                ),
                onClick = {
                    changeWillDeletedCustomer(customer.id)
                    changeShowDeletedCustomerDialog(true)
                }
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Hapus Pelanggan")
                Text(text = "Hapus")
            }
        }

    }

}

fun changePhoneFormat(value: String): String {
    return value.replaceFirstChar { "62" }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MyLaundryTheme {
        CustomerListScreen(rememberNavController())
    }
}
