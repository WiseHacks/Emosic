package com.example.emosic.screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.utils.Params
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.runBlocking


@Composable
fun ResetPasswordScreen(context: Context, navController: NavController, data : Uri) {
    var password by remember{
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Enter New Password", color = Color.Gray)
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
                        painter = painterResource(R.drawable.ic_password),
                        contentDescription = "Password Icon",
                        tint = Color.Blue,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                try {
                    val app = App.create(Params.APP_ID)
                    val token = data.getQueryParameter("token")
                    val tokenId = data.getQueryParameter("tokenId")
                    runBlocking {
                        if (token != null) {
                            if (tokenId != null) {
                                app.emailPasswordAuth.resetPassword(token, tokenId, password)
                            }
                        }
                        Toast.makeText(context, "Reset Success", Toast.LENGTH_SHORT).show()
                        navController.navigate(Params.LoginScreenRoute)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }

            }) {
                Text(text = "RESET PASSWORD")
            }

        }
    }
}