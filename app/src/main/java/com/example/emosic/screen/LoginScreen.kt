package com.example.emosic.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Down
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusDirection.Companion.Down
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.utils.BackgroundImageGuitar
import com.example.emosic.utils.Params
import com.example.emosic.utils.ProgressIndicator
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.imePadding
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.launch
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
        BackgroundImageGuitar()
        var showProgress by remember {
            mutableStateOf(false)
        }
        if(showProgress){
            ProgressIndicator()
        }
        Card(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 100.dp)
            .alpha(0.9f),
            elevation = 10.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            val focusManager = LocalFocusManager.current
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                item {
                    Box(modifier = Modifier.height(350.dp), contentAlignment = Alignment.Center) {
                        // CHANGE HEIGHT - MAYBE 0.25f
                        Image(
                            painter = painterResource(id = R.drawable.emosic_welcome),
                            contentDescription = "Welcome Icon",
                            Modifier.size(250.dp)
                        )
                    }
                }
                item {
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
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(3)
                                }
                            }
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
                }
                item {
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
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(4)
                                }
                            }
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
                }
                item {
                    Button(onClick = {
                        try {
                            showProgress = true
                            login(email, password)
                            showProgress = false
                            navController.popBackStack()
                            navController.navigate(Params.DashBoardScreenRoute)
                        } catch (e: Exception) {
                            showProgress = false
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
//                TO DO("CHECK for success login - maybe try catch will help")
                    }) {
                        Text(text = "SIGN IN")
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
                item {
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
                // OPTIONAL
                item {
                    Box(modifier = Modifier.height(400.dp))
                }
            }
        }
    }
}

fun login(email: String, password: String) {
    val app = App.create(Params.APP_ID)
    runBlocking {
        val user = app.login(Credentials.emailPassword(email, password))
//        val config = SyncConfiguration.Builder(user, setOf(User::class)).build()
//        val realm = Realm.open(config)
//        Log.e("User Login", "Logged in")
//        realm.close()
    }
}