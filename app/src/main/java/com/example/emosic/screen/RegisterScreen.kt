package com.example.emosic.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emosic.data.User
import com.example.emosic.utils.Params
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.typeOf

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
        val focusManager = LocalFocusManager.current
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                Box(modifier = Modifier.height(250.dp)) {
                    // CHANGE HEIGHT
                }
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
                Button(onClick = {
                    try {
                        runBlocking {
                            App.create(Params.APP_ID).emailPasswordAuth.registerUser(email, password)
                        }
                        register(name, age.toInt(), email, password, context)
                        navController.popBackStack()
                        navController.navigate(Params.DashBoardScreenRoute)
                    }
                    catch (e:Exception){
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
//                TO DO("CHECK for success register")
                }) {
                    Text(text = "REGISTER")
                }
            }
        }
    }
}

fun register(name : String, age : Int, email: String, password : String, context: Context){
    val app = App.create(Params.APP_ID)
    runBlocking {
        val user = app.login(Credentials.emailPassword(email, password))
        val config = SyncConfiguration.Builder(user, setOf(User::class))
            .name("realm name")
            .initialSubscriptions(initialSubscriptionBlock = {realm->
                add(
                    realm.query<User>(
                        // $0 is first argument, and then so on..
//                        "_id == $0",
//                        "${ObjectId.from(user.id)}"
                    ),
                    "subscription name"
                )
            })
            .build()
        val realm = Realm.open(config)
        realm.write {
            this.copyToRealm(User().apply {
                this._id = ObjectId.from(user.id)
                this.name = name
                this.email = email
                this.age = age
            })
        }
//        Log.e("User Login", "Logged in")
        realm.close()
    }
}
