package org.d3if3066.mylaundry.ui.screen.transaction

import CustomDatePicker
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.component.CustomTextField
import org.d3if3066.mylaundry.component.PelangganDropDown
import org.d3if3066.mylaundry.component.PriceTextField
import org.d3if3066.mylaundry.component.TipeDropDown
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.ui.theme.focusedTextFieldText
import org.d3if3066.mylaundry.ui.theme.textFieldContainer
import org.d3if3066.mylaundry.ui.theme.unfocusedTextFieldText
import org.d3if3066.mylaundry.util.ViewModelFactory
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
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
        AddTransactionScreenContent(Modifier.padding(padding), navController)
    }
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTransactionScreenContent(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(
        serviceDao = db.serviceDao,
        orderDao = db.orderDao,
        customerDao = db.customerDao
    )
    val viewModel: TransactionViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()
    val serviceList by viewModel.serviceList.collectAsState()
    val customerList by viewModel.customerList.collectAsState()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val current = LocalDateTime.now().format(formatter)

    var berat by remember { mutableStateOf("") }
    var tipeLaundry by remember { mutableStateOf("") }
    var pelanggan by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(current) }
    var endDate by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }

    val selectedService = serviceList.firstOrNull { it.name == tipeLaundry }


    val totalPrice = if (selectedService != null) {
        if (berat == "") 0.0 else berat.toDouble() * selectedService.price
    } else {
        0.0
    }

    price = totalPrice

    if (serviceList.isEmpty()){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Buat layanan terlebih dahulu")
        }
        return
    }

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
                    label = "Nama Pelanggan",
                    trailing = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    value = pelanggan,
                    onValueChange = { pelanggan = it },
                    customerList = customerList
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    value = customerPhone,
                    onValueChange = { customerPhone = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(text = "08XXXXXXXXXX")
                    },
                    label = {
                        Text(
                            text = "Nomor HP Pelanggan (Opsional)",
                            style = MaterialTheme.typography.labelMedium,
                            color = CustomBlackPurple
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                        unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
                    ),


                    )
                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    label = "Berat",
                    trailing = {
                        Text(text = "Kg", color = CustomBlackPurple)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    value = berat,
                    onValueChange = { berat = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
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
                    onValueChange = { price = it.toDouble() },
                    supportingText = {
                        Text(
                            text = "Rp. " + DecimalFormat("#,###.##").format(
                                selectedService?.price ?: 0
                            ) + " × " + (if (berat == "") "0" else berat) + " Kg = Rp. " + DecimalFormat(
                                "#,###.##"
                            ).format(price)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = {
                        if (
                            pelanggan == "" &&
                            berat == ""
                        ) {
                            Toast.makeText(
                                context,
                                "Data tidak boleh kososng",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        coroutineScope.launch {
                            if (viewModel.createTransaction(
                                    customerName = pelanggan,
                                    weight = if (berat !== "") berat.toInt() else 0,
                                    serviceName = tipeLaundry,
                                    startDate = startDate,
                                    endDate = endDate,
                                    price = price.toInt(),
                                    customerPhone = customerPhone,
                                )
                            ) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.data_added_success_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Screen.TransactionList.route) {
                                    popUpTo(Screen.AddTransaction.route) {
                                        inclusive = true
                                    }
                                }
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

//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentPadding = PaddingValues(vertical = 13.dp),
//                    onClick = {
//                        shareData(
//                            context = context,
//                            message = context.getString(
//                                R.string.bagikan_template,
//                                pelanggan,
//                                berat,
//                                tipeLaundry,
//                                startDate,
//                                endDate,
//                                price
//                            )
//                        )
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = CustomBlackPurple,
//                        contentColor = CustomWhite
//                    ),
//                    shape = RoundedCornerShape(size = 4.dp)
//                ) {
//                    Text(text = "Kirim", style = MaterialTheme.typography.labelMedium)
//                }
//                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}

fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun TransScreenPreview() {
    MyLaundryTheme {
//        AddTransactionScreen(rememberNavController())
    }
}
