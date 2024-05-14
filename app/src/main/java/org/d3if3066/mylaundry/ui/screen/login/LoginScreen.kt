package org.d3if3066.mylaundry.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.R
import org.d3if3066.mylaundry.component.CustomTextField
import org.d3if3066.mylaundry.component.PassTextField
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.navigation.Screen
import org.d3if3066.mylaundry.ui.theme.CustomBlackPurple
import org.d3if3066.mylaundry.ui.theme.CustomBlue
import org.d3if3066.mylaundry.ui.theme.CustomPurple
import org.d3if3066.mylaundry.ui.theme.CustomWhite
import org.d3if3066.mylaundry.ui.theme.MyLaundryTheme
import org.d3if3066.mylaundry.util.ViewModelFactory

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(db.userDao)
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        if (viewModel.checkIsSignedIn()){
            navHostController.navigate(Screen.Home.route)
        }
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Surface() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.46f),
                    painter = painterResource(id = R.drawable.shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Row(
                    modifier = Modifier.padding(top = 80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = CustomBlue)) {
                                    append("My")
                                }
                                withStyle(SpanStyle(color = Color.White)) {
                                    append("Laundry")
                                }
                            },
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text(
                            text = stringResource(R.string.tagline),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(alignment = Alignment.BottomCenter),
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineLarge,
                    color = CustomBlackPurple,
                )
            }

            //Bottom section
            Spacer(modifier = Modifier.height(36.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                CustomTextField(
                    label = "Email",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = {email= it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                PassTextField(
                    label = "Password",
                    trailing = "",
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (viewModel.login(
                                    email,
                                    password
                                )
                            ) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.login_success_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navHostController.navigate(Screen.Home.route)
                            }
                            else Toast.makeText(
                                context,
                                context.getString(R.string.login_failed_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomBlackPurple,
                        contentColor = CustomWhite
                    ),
                    shape = RoundedCornerShape(size = 4.dp)
                ) {
                    Text(text = "Login", style = MaterialTheme.typography.labelMedium)
                }

                ClickableText(
                    modifier = Modifier.padding(top = 16.dp),
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = CustomBlackPurple)) {
                            append(stringResource(R.string.dont_have_account))
                        }
                        withStyle(SpanStyle(color = CustomPurple)) {
                            append(stringResource(id = R.string.register))
                        }
                    },
                    onClick = { navHostController.navigate(Screen.Register.route) },
                    style = MaterialTheme.typography.labelMedium
                )


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyLaundryTheme {
        LoginScreen(navHostController = rememberNavController())
    }
}