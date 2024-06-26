package org.d3if3066.mylaundry

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3066.mylaundry.navigation.SetupNavGraph
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyLaundryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph()
//                    CustomDatePicker(this)
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLaundryTheme {
        SetupNavGraph()
    }
}