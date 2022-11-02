package com.example.emosic.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emosic.MainActivity
import com.example.emosic.data.User
import com.example.emosic.utils.Params
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                singleLine = true,
            )
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
                singleLine = true,
            )
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
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                try {
                    runBlocking {
                        App.create(Params.APP_ID).emailPasswordAuth.registerUser(email, password)
                    }
                    register(name, age.toInt(), email, password, context)
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
