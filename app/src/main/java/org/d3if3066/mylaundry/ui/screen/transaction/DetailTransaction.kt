package org.d3if3066.mylaundry.ui.screen.transaction

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.util.ViewModelFactory

const val KEY_ID_ORDER = "idOrder"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaction(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        serviceDao = db.serviceDao,
        orderDao = db.orderDao,
        customerDao = db.customerDao
    )
    val viewModel: AddTransactionViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()
    val serviceList by viewModel.serviceList.collectAsState()
    val customerList by viewModel.customerList.collectAsState()


    var berat by remember { mutableStateOf("") }
    var tipeLaundry by remember { mutableStateOf("") }
    var pelanggan by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }

    val selectedService = serviceList.firstOrNull { it.name == tipeLaundry }
//    val customerState = viewModel.getCustomerById(order.customerId).collectAsState()
//    val customer = customerState.value
//
//    val serviceState = viewModel.getServiceById(order.serviceId).collectAsState()
//    val service = serviceState.value


    val totalPrice = if (selectedService != null) {
        berat.toDouble() * selectedService.price
    } else {
        0.0
    }

    price = totalPrice

    LaunchedEffect(true) {
        if(id == null) return@LaunchedEffect
        val data = viewModel.getOrder(id) ?: return@LaunchedEffect
//        if (customer != null) {
//            pelanggan = customer.name.toString()
//        }
//        berat = data.weight.toString()
//        if (service != null) {
//            tipeLaundry = service.name.toString()
//        }
        startDate = data.startDate
        endDate = data.endDate
        price = data.price.toDouble()

    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() })
                    {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if(id == null) {
                        Text(text = stringResource(id = R.string.tambah_transaksi))
                    }
                    else {
                        Text(text = stringResource(id = R.string.edit_transaksi))
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (pelanggan == "" || berat == "" || tipeLaundry == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        //
                        navController.popBackStack()
                    })
                    {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
//                    if(id != null) {
//                        DeleteAction {
//                            showDialog = true
//                        }
//
//                        DisplayAlertDialog(
//                            openDialog = showDialog,
//                            onDismissRequest = {showDialog = false}
//                        ) {
//                            showDialog = false
//                            viewModel.delete(id)
//                            navController.popBackStack()
//                        }
//                    }
                }
            )
        }
    ) {
            padding -> FormEditTransaksi(modifier = Modifier.padding(padding))
    }
}

@Composable
fun FormEditTransaksi(modifier: Modifier) {

}
@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}
