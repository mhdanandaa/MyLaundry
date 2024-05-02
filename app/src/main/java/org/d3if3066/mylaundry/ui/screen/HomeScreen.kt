package org.d3if3066.mylaundry.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomLightBlue
import org.d3if3066.mylaundry.ui.theme.CustomLightGreen
import org.d3if3066.mylaundry.ui.theme.CustomLightPurple
import org.d3if3066.mylaundry.ui.theme.CustomLightRed
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme

@Composable
fun HomeScreen(navHostController: NavHostController) {
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
                            .padding(start = 20.dp, top = 10.dp)
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = CustomBlackPurple,
                        text = "Rp. 100.000.000,00"
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
                        onClick = { /*TODO*/ },
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

                    //Button Riwayat
                    Button(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(160.dp, 120.dp)
                        ,
                        onClick = { /*TODO*/ },
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

                    // Button Pelanggan
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .size(160.dp, 120.dp)
                        ,
                        onClick = { /*TODO*/ },
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
                }


                //Tombol Transaksi
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomLightPurple,
                        contentColor = CustomBlackPurple
                    ),
                    shape = RoundedCornerShape(size = 15.dp)
                ) {
                    Text(
                        text = "+ Transaksi",
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