package org.d3if3066.mylaundry.ui.screen.transaction

import CustomDatePicker
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.component.CustomTextField
import org.d3if3066.mylaundry.component.PelangganDropDown
import org.d3if3066.mylaundry.component.PriceTextField
import org.d3if3066.mylaundry.component.TipeDropDown
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.Service
import org.d3if3066.mylaundry.ui.screen.service.AddServiceViewModel
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(navController: NavHostController) {
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
                title = { Text(text = stringResource(id = R.string.tambah_transaksi)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = CustomPurple,
                    //Untuk Judul
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ScreenContent(modifier: Modifier) {
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
    val price by remember { mutableDoubleStateOf(0.0) }

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Bagian Banner (Banner Content)
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.home_banner),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 30.dp)
                        .align(alignment = Alignment.TopStart),
                    text = "Laundry Ahlan Wa Sahlan",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                )

                Image(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .padding(end = 20.dp),
                    painter = painterResource(id = R.drawable.logo_laundry_home),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.tambah_transaksi),
                    style = MaterialTheme.typography.headlineMedium,
                    color = CustomBlackPurple,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                PelangganDropDown(
                    label = "Pelanggan",
                    trailing = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    value = pelanggan,
                    onValueChange = { pelanggan = it },
                    customerList = customerList
                )
                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    label = "Berat",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth(),
                    value = berat,
                    onValueChange = { berat = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TipeDropDown(
                    label = "Tipe Laundry",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth(),
                    value = tipeLaundry,
                    onValueChange = { tipeLaundry = it },
                    serviceList = serviceList
                )
                Spacer(modifier = Modifier.height(20.dp))

                CustomDatePicker(
                    label = "Tanggal Pengantaran",
                    contex = context,
                    value = startDate,
                    onValueChange = { startDate = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                CustomDatePicker(
                    "Tanggal Pengambilan",
                    contex = context,
                    value = endDate,
                    onValueChange = { endDate = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                PriceTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Total Harga",
                    enabled = true,
                    value = price,
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (viewModel.createTransaction(
                                    customerName = pelanggan,
                                    weight = berat.toInt(),
                                    serviceName = tipeLaundry,
                                    startDate = startDate,
                                    endDate = endDate,
                                    price = price.toInt()
                                )
                            ) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.data_added_success_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomBlackPurple,
                        contentColor = CustomWhite
                    ),
                    shape = RoundedCornerShape(size = 4.dp)
                ) {
                    Text(text = "Tambah", style = MaterialTheme.typography.labelMedium)
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun TransScreenPreview() {
    MyLaundryTheme {
        AddTransactionScreen(rememberNavController())
    }
}
