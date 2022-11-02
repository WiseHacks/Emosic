package com.example.emosic.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emosic.utils.Params
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.runBlocking

@Composable
fun LoginScreen(
    context: Context,
    navController: NavController
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = "Enter Email Address", color = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray,
                    textColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = Color.Blue,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Enter Password", color = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray,
                    textColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(com.example.emosic.R.drawable.ic_password),
                        contentDescription = "Password Icon",
                        tint = Color.Blue,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                try {
                    login(email, password)
                    navController.navigate(Params.DashBoardScreenRoute)
                }catch (e:Exception){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
//                TO DO("CHECK for success login - maybe try catch will help")
            }) {
                Text(text = "SIGN IN")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Forgot password?", modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Params.ForgotPasswordScreenRoute)
                    },
                textAlign = TextAlign.Center,
                color = Color.Blue,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "New user? Register here", modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Params.RegisterScreenRoute)
                    },
                textAlign = TextAlign.Center,
                color = Color.Blue,
                fontSize = 18.sp
            )
        }
    }
}

fun login(email : String, password : String){
    val app = App.create(Params.APP_ID)
    runBlocking {
        val user = app.login(Credentials.emailPassword(email, password))
//        val config = SyncConfiguration.Builder(user, setOf(User::class)).build()
//        val realm = Realm.open(config)
//        Log.e("User Login", "Logged in")
//        realm.close()
    }
}