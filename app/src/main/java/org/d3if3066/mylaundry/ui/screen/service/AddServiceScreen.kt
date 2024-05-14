package org.d3if3066.mylaundry.ui.screen.service

import android.widget.Toast
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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.ui.screen.customer.AddCustomerViewModel
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServiceScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White)
                    }
                },
                title = { Text(text = stringResource(id = R.string.tambah_layanan)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = CustomPurple,
                    //Untuk Judul
                    titleContentColor = Color.White
                ))
        }
    ) {padding ->
        ServicesContent(Modifier.padding(padding))
    }
}

@Composable
fun ServicesContent(modifier: Modifier) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(serviceDao = db.serviceDao)
    val viewModel: AddServiceViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    var serviceName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

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
                    text = stringResource(R.string.tambah_layanan),
                    style = MaterialTheme.typography.headlineMedium,
                    color = CustomBlackPurple,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                CustomTextField(
                    label = "Jenis Layanan",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp),
                    value = serviceName,
                    onValueChange = {serviceName = it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    label = "Harga",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth(),
                    value = price,
                    onValueChange = {price = it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = {
                        coroutineScope.launch {
                            if(viewModel.createService(name = serviceName, price = price.toInt())){
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
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    MyLaundryTheme {
        AddServiceScreen(rememberNavController())
    }
}
