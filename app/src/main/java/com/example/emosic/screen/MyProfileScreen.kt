package com.example.emosic.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.data.User
import com.example.emosic.repository.UserDataRepositoryImpl
import com.example.emosic.repository.UserDataRepositoryImpl_
import com.example.emosic.ui.theme.InitialDashboardTextColor
import com.example.emosic.ui.theme.OnclickProfileButtonColor
import com.example.emosic.ui.theme.ProfileButtonColor
import com.example.emosic.ui.theme.ProfileTextColor
import com.example.emosic.utils.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MyProfileScreen(
    context: Context,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BackgroundImageApe()
        var showProgress by remember {
            mutableStateOf(true)
        }
        var user:User? by remember {
            mutableStateOf(null)
        }
        LaunchedEffect(key1 = showProgress){
            if(UserDataRepositoryImpl_.user == null){
                UserDataRepositoryImpl_.getUserData()
            }
            user = UserDataRepositoryImpl_.user
//            user = UserDataRepositoryImpl().getUserData()
            showProgress = false
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 100.dp)
                .alpha(0.9f),
            elevation = 10.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 50.dp)
                    .alpha(0.9f),
                border = BorderStroke(3.dp, color = ProfileTextColor),
                shape = RoundedCornerShape(30.dp)
            ) {
                if(!showProgress){
                    Log.v("fetch test", user.toString())
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            WelcomeImage(200.dp)
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    text = "Name",
                                    fontSize = 25.sp,
                                    fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                    fontWeight = FontWeight.Bold,
                                    color = ProfileTextColor
                                )
                                user?.let {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(1f),
                                        text = it.name,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                        color = ProfileTextColor
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    text = "Age",
                                    fontSize = 25.sp,
                                    fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                    fontWeight = FontWeight.Bold,
                                    color = ProfileTextColor
                                )
                                user?.let {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(1f),
                                        text = it.age.toString(),
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                        color = ProfileTextColor
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    text = "Email",
                                    fontSize = 25.sp,
                                    fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                    fontWeight = FontWeight.Bold,
                                    color = ProfileTextColor
                                )
                                user?.let {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(1f),
                                        text = it.email,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                        color = ProfileTextColor
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(50.dp))
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                var colorButton1 by remember {
                                    mutableStateOf(Color.White)
                                }
                                var colorButton2 by remember {
                                    mutableStateOf(Color.White)
                                }
                                Card(modifier = Modifier.fillMaxWidth(0.5f)
                                    .padding(horizontal = 5.dp)
                                    .clickable {
                                        colorButton1 = OnclickProfileButtonColor
                                        navController.navigate(Params.LikedSongsScreenRoute)
                                    },
                                    shape = RoundedCornerShape(30.dp),
                                    backgroundColor = ProfileButtonColor
                                    ){
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                        Text(
                                            modifier = Modifier.fillMaxWidth(1f),
                                            text = "Liked songs",
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                            color = colorButton1
                                        )
                                    }
                                }
                                Card(modifier = Modifier.fillMaxWidth(1f)
                                    .padding(horizontal = 5.dp)
                                    .clickable {
                                        colorButton2 = OnclickProfileButtonColor
                                        val builder = android.app.AlertDialog.Builder(context)
                                        builder.setTitle("confirm sign-out")
                                        builder.setMessage("Are you sure?")
                                        builder.setPositiveButton("Yes") { dialog, which ->
                                            FirebaseAuth.getInstance().signOut()
                                            navController.navigate(Params.LoginScreenRoute){
                                                popUpTo(0)
                                            }
                                        }
                                        builder.setNegativeButton("No") { dialog, which ->
                                            colorButton2 = Color.White
                                        }
                                        builder.show()
                                    },
                                    shape = RoundedCornerShape(30.dp),
                                    backgroundColor = ProfileButtonColor
                                ){
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                        Text(
                                            modifier = Modifier.fillMaxWidth(1f),
                                            text = "Sign out", //LikedChannels was here
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                            color = colorButton2
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }
                if(showProgress) {
                    ProgressIndicator()
                }
            }
        }
    }
}