package org.d3if3066.mylaundry.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CustomPurple = Color(4286865379)
val CustomBlackPurple = Color(4281665102)
val CustomBlue = Color(4287290835)
val CustomWhite = Color(4294047227)


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val ColorScheme.focusedTextFieldText
@Composable
get() = CustomBlackPurple

val ColorScheme.unfocusedTextFieldText
    @Composable
    get() = CustomBlackPurple

val ColorScheme.textFieldContainer
    @Composable
    get() = CustomWhite
