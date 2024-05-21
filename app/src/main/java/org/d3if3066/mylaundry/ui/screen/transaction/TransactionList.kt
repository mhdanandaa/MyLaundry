package org.d3if3066.mylaundry.ui.screen.transaction

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.daftar_transaksi)) },
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
                    navController.navigate(Screen.Transaction.route)
                },
                containerColor = CustomPurple
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_transaksi),
                    tint = CustomWhite
                )
            }
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding), navController)

    }

}

@Composable
fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        orderDao = db.orderDao,
        serviceDao = db.serviceDao,
        customerDao = db.customerDao
    )
    val viewModel: AddTransactionViewModel = viewModel(factory = factory)
    val data by viewModel.orderList.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong))
        }

    }
    else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) { order ->
                ListItems(order = order, onClick = { /* Handle item click */ }, viewModel = viewModel)
                Divider()
            }
        }
    }
}

@Composable
fun ListItems(order: Order, onClick: () -> Unit, viewModel: AddTransactionViewModel) {
    val customerState = viewModel.getCustomerById(order.customerId).collectAsState()
    val customer = customerState.value

    val serviceState = viewModel.getServiceById(order.serviceId).collectAsState()
    val service = serviceState.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        Text(
            text = customer?.name ?: "Loading..",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = service?.name ?: "Loading..",
            fontWeight = FontWeight.Bold
        )

        Text(text = "Berat : " + order.weight.toString()+" KG")
        Text(text = "Tanggal Masuk : " + order.startDate)
        Text(text = "Tanggal Selesai : " + order.endDate)
        Text(text = "Total Harga : " + order.price.toString())
    }

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MyLaundryTheme {
        TransactionListScreen(rememberNavController())
    }
}
