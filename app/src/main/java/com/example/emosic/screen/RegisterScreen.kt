package com.example.emosic.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.data.User
import com.example.emosic.utils.BackgroundImageGuitar
import com.example.emosic.utils.Params
import com.example.emosic.utils.ProgressIndicator
import com.example.emosic.utils.WelcomeImage
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

@Composable
fun RegisterUserScreen(context: Context, navController: NavController) {
    // name, age, email, phone, password
    var name by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
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
                    WelcomeImage()
                }
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(text = "Enter Full Name", color = Color.Gray)
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
                    )
                }
                item {
                    OutlinedTextField(
                        value = age,
                        onValueChange = {
                            age = it
                        },
                        label = {
                            Text(text = "Enter Age", color = Color.Gray)
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
                            keyboardType = KeyboardType.Number,
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
                    )
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
                                    listState.animateScrollToItem(5)
                                }
                            }
                        ),
                        singleLine = true,
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
                                    listState.animateScrollToItem(6)
                                }
                            }
                        ),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val color = if (isPressed) Color.Gray else Color(0xFFFF6200EE)
                    var res by remember{
                        mutableStateOf("")
                    }
                    Button(onClick = {
                        try {
                            showProgress = true
                            CoroutineScope(Dispatchers.IO).launch {
                                val user = User(name = name, email = email, age = age.toInt())
                                res = register(user, password).toString()
                            }
                            if(res != null){
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                                navController.navigate(Params.DashBoardScreenRoute)
                            }
                        } catch (e: Exception) {
                            showProgress = false
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
//                TO DO("CHECK for success register")
                    },
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(backgroundColor = color)
                    ) {
                        Text(text = "REGISTER")
                    }
                }
                // OPTIONAL - also in LOGin
                item {
                    Box(modifier = Modifier.height(400.dp))
                }
            }
            if(showProgress){
                ProgressIndicator()
            }
        }
    }
}

suspend fun register(user: User, password : String) : Void? {
    return try {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, password).await()
        val db = FirebaseFirestore.getInstance()
        val temp = HashMap<String, Any>()
        temp["name"] = user.name
        temp["age"] = user.age
        temp["email"] = user.email
        temp["likedSongs"] = user.likedSongs
        temp["subscribedChannels"] = user.subscribedChannels
        db.collection("User")
            .document(auth.currentUser?.uid.toString())
            .set(temp)
            .await()
    }catch(e:Exception){
        return null
    }
}
