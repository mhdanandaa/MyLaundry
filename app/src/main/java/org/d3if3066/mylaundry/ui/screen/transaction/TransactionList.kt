package org.d3if3066.mylaundry.ui.screen.transaction

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.component.DisplayAlertDialog
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.model.OrderDetail
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack(route = Screen.Home.route, inclusive = false) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
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
                    navController.navigate(Screen.AddTransaction.route)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        orderDao = db.orderDao,
        serviceDao = db.serviceDao,
        customerDao = db.customerDao
    )
    val viewModel: TransactionViewModel = viewModel(factory = factory)
    val data by viewModel.orderDetailList.collectAsState()
//    Log.d("delete",data.toString())
    var showDeleteOrderDialog by remember { mutableStateOf(false) }
    var willDeletedOrder by remember { mutableStateOf(0L) }

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

    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) {

                ListItems(
                    order = it,
                    onClick = { navController.navigate(Screen.DetailTransaction.withId(it.id)) },
                    viewModel = viewModel,
                    changeWillDeletedOrder = { willDeletedOrder = it },
                    onShowDeleteOrderDialogChange = { showDeleteOrderDialog = it }
                )
                Divider()
            }
        }
        DisplayAlertDialog(
            openDialog = showDeleteOrderDialog,
            onDismissRequest = { showDeleteOrderDialog = false }
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteOrder(willDeletedOrder)
            }
            showDeleteOrderDialog = false
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListItems(
    order: OrderDetail,
    onClick: () -> Unit,
    viewModel: TransactionViewModel,
    changeWillDeletedOrder: (it: Long) -> Unit,
    onShowDeleteOrderDialogChange: (it: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth(0.85f),
//            verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {
            Text(
                text = order?.customerName ?: "Anonymous",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = (order?.serviceName
                    ?: "Tanpa Layanan") + " | " + order.weight.toString() + " Kg",
//                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Mulai : " + changeDateFormat(order.startDate),
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Selesai : " + changeDateFormat(order.endDate),
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Rp. " + DecimalFormat("#,###.##").format(order.price),
                color = Color.Blue,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

                changeWillDeletedOrder(order.id)
                onShowDeleteOrderDialogChange(true)
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "sas", tint = Color.Red)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun changeDateFormat(output: String): String {
    if (output !== "") {
        val outputDate = LocalDate.parse(
            output,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
        return "${outputDate.dayOfWeek.toString().lowercase().replaceFirstChar(Char::titlecase) }, ${outputDate.dayOfMonth} ${outputDate.month.toString().lowercase().replaceFirstChar(Char::titlecase)} ${outputDate.year}"
    } else {
        return ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MyLaundryTheme {
        TransactionListScreen(rememberNavController())
    }
}
