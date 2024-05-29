package org.d3if3066.mylaundry.ui.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.model.OrderDetail
import org.d3if3066.mylaundry.model.User
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomLightBlue
import org.d3if3066.mylaundry.ui.theme.CustomLightGreen
import org.d3if3066.mylaundry.ui.theme.CustomLightPurple
import org.d3if3066.mylaundry.ui.theme.CustomLightRed
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory
import java.text.DecimalFormat
import java.util.Calendar

const val KEY_NEXT_PAGE = "nextPage"
@Composable
fun HomeScreen(navHostController: NavHostController,nextPage:String? = null) {
    if (nextPage !== null) navHostController.navigate(nextPage)
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(userDao = db.userDao, orderDao = db.orderDao)
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val listOfMonth = listOf("Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember")
    var currentMonthRevenue by remember {
        mutableStateOf(0)
    }

    var user by remember {
        mutableStateOf<User>(User(0,"Hallo","johndoe@gmail.com","123",true))
    }
    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    LaunchedEffect(true ){
        user = viewModel.getSignedInUser()!!
        currentMonthRevenue += viewModel.getCurrentMonthRevenue()
    }
    Surface () {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {

            // Bagian Banner (Banner Content)
            Box(
                contentAlignment = Alignment.TopCenter
            ){
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
                    text = "Laundry ${user.laundryName}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )

                Image(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .padding(end = 20.dp, top = 20.dp),
                    painter = painterResource(id = R.drawable.logo_laundry_home),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }


            //Bottom section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {

                //Total Penghasilan
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .background(color = CustomLightPurple, shape = RoundedCornerShape(15.dp))
                        .size(150.dp),

                ){
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 10.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CustomBlackPurple,
                        text = "Total Penghasilan :"
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = CustomBlackPurple,
                        text = "Rp. "+ DecimalFormat("#,###.##").format(currentMonthRevenue),
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp, bottom = 10.dp)
                            .align(Alignment.BottomStart),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = CustomBlackPurple,
                        text = "Penghasilan pada bulan "+listOfMonth[month]+" "+year
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .size(270.dp),
                ){
                    //Button Layanan
                    Button(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(160.dp, 120.dp)
                        ,
                        onClick = {navHostController.navigate(Screen.ServiceList.route)},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomLightRed,
                            contentColor = CustomBlackPurple
                        ),
                        shape = RoundedCornerShape(size = 15.dp)
                    ){
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                modifier = Modifier.size(70.dp),
                                painter = painterResource(id = R.drawable.layanan),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )

                            Text(
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CustomBlackPurple,
                                text = "Layanan"
                            )
                        }

                    }
                    // Button Pelanggan
                    Button(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(160.dp, 120.dp),
                        onClick = {navHostController.navigate(Screen.CustomerList.route)},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomLightBlue,
                            contentColor = CustomBlackPurple
                        ),
                        shape = RoundedCornerShape(size = 15.dp)
                    ){
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                modifier = Modifier.size(70.dp),
                                painter = painterResource(id = R.drawable.pelanggan),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                            )

                            Text(
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CustomBlackPurple,
                                text = "Pelanggan"
                            )
                        }

                    }
                    //Button Riwayat
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .size(160.dp, 120.dp),
                        onClick = {navHostController.navigate(Screen.TransactionList.route)},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomLightGreen,
                            contentColor = CustomBlackPurple
                        ),
                        shape = RoundedCornerShape(size = 15.dp)
                    ){
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                modifier = Modifier.size(70.dp),
                                painter = painterResource(id = R.drawable.riwayat),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )

                            Text(
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CustomBlackPurple,
                                text = "Riwayat"
                            )
                        }

                    }


                }


                //Tombol Transaksi
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = {navHostController.navigate(Screen.AddTransaction.route)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomLightPurple,
                        contentColor = CustomBlackPurple
                    ),
                    shape = RoundedCornerShape(size = 15.dp)
                ) {
                    Text(
                        text = "+ Tambah Transaksi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyLaundryTheme {
        HomeScreen(navHostController = rememberNavController())
    }
}