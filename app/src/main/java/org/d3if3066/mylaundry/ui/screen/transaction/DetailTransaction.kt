package org.d3if3066.mylaundry.ui.screen.transaction

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.ui.screen.customer.changePhoneFormat
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.util.ViewModelFactory
import java.text.DecimalFormat

const val KEY_ID_ORDER = "idOrder"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaction(navController: NavHostController, id: Long) {

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
                title = { Text(text = "Detail Pesanan") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = CustomPurple,
                    //Untuk Judul
                    titleContentColor = CustomWhite
                )
            )
        },
    ) { padding ->
        DetailTransactionScreenContent(
            modifier = Modifier.padding(padding),
            id = id
        )
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailTransactionScreenContent(
    modifier: Modifier,
    id: Long
) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        serviceDao = db.serviceDao,
        orderDao = db.orderDao,
        customerDao = db.customerDao
    )
    val viewModel: TransactionViewModel = viewModel(factory = factory)

    val orderDetail = viewModel.getOrderDetailById(id)
    if (orderDetail == null) Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Data Tidak Ditemukan")
    } else
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(
                    text = "Nama Pelanggan",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = orderDetail.customerName, style = MaterialTheme.typography.titleLarge)
            }
            Column {
                Text(
                    text = "Nomor Handphone Pelanggan",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (orderDetail.customerPhone != "") orderDetail.customerPhone else "Tidak Ada",
                    style = MaterialTheme.typography.titleLarge
                )

            }
            LazyVerticalGrid(modifier = Modifier.fillMaxWidth(), columns = GridCells.Fixed(2)) {
                item {
                    Column {
                        Text(
                            text = "Tanggal Pengantaran",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = changeDateFormat(orderDetail.startDate),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                item {
                    Column {
                        Text(
                            text = "Tanggal Pengambilan",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = changeDateFormat(orderDetail.endDate),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            Column {
                Text(
                    text = "Jenis Layanan Laundry",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = orderDetail.serviceName, style = MaterialTheme.typography.titleLarge)
            }
            Column {
                Text(
                    text = "Berat Laundry",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = orderDetail.weight.toString() + " Kg",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Column {
                Text(
                    text = "Harga Total",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Rp. " + DecimalFormat("#,###.##").format(orderDetail.price),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Red
                )
            }
            if (orderDetail.customerPhone != "") {
                Spacer(modifier = Modifier.padding(2.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = CustomPurple),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                    "https://api.whatsapp.com/send?phone=" + changePhoneFormat(
                                        orderDetail.customerPhone
                                    ) + "&text=" + context.getString( R.string.bagikan_template,
                                        orderDetail.customerName,
                                        orderDetail.weight.toString(),
                                        orderDetail.serviceName,
                                        changeDateFormat(orderDetail.startDate),
                                        changeDateFormat(orderDetail.endDate),
                                        orderDetail.price.toFloat())
                                )
                            )
                        )
                    }) {
                    Text(text = "Chat Whatsapp")
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(
                            R.string.bagikan_template,
                            orderDetail.customerName,
                            orderDetail.weight.toString(),
                            orderDetail.serviceName,
                            changeDateFormat(orderDetail.startDate),
                            changeDateFormat(orderDetail.endDate),
                            orderDetail.price.toFloat()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomBlackPurple,
                    contentColor = CustomWhite
                ),
            ) {
                Text(text = "Bagikan", style = MaterialTheme.typography.labelMedium)
            }
        }
}
