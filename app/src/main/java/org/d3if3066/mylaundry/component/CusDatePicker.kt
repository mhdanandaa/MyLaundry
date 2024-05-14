import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import java.util.Date

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CustomDatePicker(
    label: String,
    contex: Context,
    value:String,
    onValueChange:(it:String)->Unit
) {
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = android.app.DatePickerDialog(
        contex,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, dayOfMonth: Int ->
            onValueChange("$dayOfMonth/$selectedMonth/$selectedYear")
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { datePickerDialog.show() }
                .background(CustomWhite)
                .padding(horizontal = 20.dp, vertical = 17.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "$label : ${value}" ,
                style = MaterialTheme.typography.labelMedium,
                color = CustomBlackPurple,
            )
        }


    }
}
