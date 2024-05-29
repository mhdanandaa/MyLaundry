package org.d3if3066.mylaundry.ui.screen.service

import org.d3if3066.mylaundry.model.Service
import org.d3if3066.mylaundry.ui.screen.transaction.TransactionViewModel

import android.content.res.Configuration
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(navController: NavHostController) {

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
                title = { Text(text = stringResource(id = R.string.daftar_layanan)) },
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
                    navController.navigate(Screen.AddService.route)
                },
                containerColor = CustomPurple
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_layanan),
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
    val viewModel: ServiceViewModel = viewModel(factory = factory)
    val data by viewModel.serviceList.collectAsState()
    var showDeleteServiceDialog by remember { mutableStateOf(false) }
    var willDeletedService by remember { mutableStateOf(0L) }

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong_service))
        }

    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) {
                ListItems(
                    service = it,
                    onClick = {},
                    changeShowDeleteServiceDialog = { showDeleteServiceDialog = it },
                    changeWillDeletedService = { willDeletedService = it },
                    )
                Divider()
            }
        }
        DisplayAlertDialog(
            openDialog = showDeleteServiceDialog,
            onDismissRequest = { showDeleteServiceDialog = false }
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteService(willDeletedService)
            }
            showDeleteServiceDialog = false
        }
    }
}

@Composable
fun ListItems(
    service: Service,
    onClick: () -> Unit,
    changeShowDeleteServiceDialog: (it: Boolean) -> Unit,
    changeWillDeletedService: (it: Long) -> Unit,
) {
    Row(
        modifier = Modifier.clickable {  },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
            Text(
                text = "Jenis Layanan : " + service.name,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Harga : Rp. ${DecimalFormat("#,###.##").format(service.price)} / Kg")
        }
        IconButton(onClick = {
            changeWillDeletedService(service.id)
            changeShowDeleteServiceDialog(true)
        }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Hapus Layanan",
                tint = Color.Red
            )
        }

    }


}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MyLaundryTheme {
        ServiceListScreen(rememberNavController())
    }
}
