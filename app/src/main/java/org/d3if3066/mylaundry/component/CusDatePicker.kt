import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDatePicker(
    label: String,
    contex: Context,
    value: String,
    onValueChange: (it: String) -> Unit
) {
    val year: Int
    val month: Int
    val day: Int
    var output = value
    if (output !== "") {
        val outputDate = LocalDate.parse(
            output,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
        output = "${outputDate.dayOfWeek.toString().lowercase().replaceFirstChar(Char::titlecase) }, ${outputDate.dayOfMonth} ${outputDate.month.toString().lowercase().replaceFirstChar(Char::titlecase)} ${outputDate.year}"
    }

    val decimalFormatter = DecimalFormat("00");
    val decimalFormatterYear = DecimalFormat("0000");
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = android.app.DatePickerDialog(
        contex,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, dayOfMonth: Int ->
            onValueChange(decimalFormatterYear.format(selectedYear)+"-"+decimalFormatter.format(selectedMonth+1)+"-"+decimalFormatter.format(dayOfMonth)+" 00:00:00")
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            TextField(
//                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Picker")
                },
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { datePickerDialog.show() },
//                .background(CustomWhite),
//                .padding(horizontal = 20.dp, vertical = 17.dp),
                value = output,
                onValueChange = {},
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                },
//            colors = TextFieldDefaults.colors(
//                unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
//                focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
//                unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
//                focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
//            ),
            )

//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .clickable { datePickerDialog.show() }
//                .background(CustomWhite)
//                .padding(horizontal = 20.dp, vertical = 17.dp),
//            contentAlignment = Alignment.CenterStart,
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "$label : ${value}",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = CustomBlackPurple,
//                )
//                Icon(
//                    imageVector = Icons.Filled.DateRange,
//                    contentDescription = "Icon Kalender",
//                    tint = CustomBlackPurple
//                )
//            }
//
//        }


    }
}
