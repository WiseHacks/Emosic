package com.example.emosic.screen

import android.content.Context
import android.view.View.OnLongClickListener
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.ui.theme.BorderColor
import com.example.emosic.ui.theme.InitialDashboardTextColor
import com.example.emosic.ui.theme.OnclickDashboardTextColor
import com.example.emosic.utils.*
import com.google.firebase.auth.FirebaseAuth
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.runBlocking

@Composable
fun DashBoardScreen(
    context: Context,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImageMic()
        var showProgress by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 100.dp)
                .alpha(0.9f),
            elevation = 10.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    WelcomeImage(230.dp)
                }
                item {
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                navController.navigate(Params.CapturePhotoScreenRoute)
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp)
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Open Camera",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(
                        color = BorderColor,
                        thickness = 3.dp,
                        modifier = Modifier.padding(horizontal = 120.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                navController.navigate(Params.RecommendationsScreenRoute)
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp)
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Recommendations",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }
                /*item {
                    Spacer(modifier = Modifier.height(10.dp))
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                navController.navigate(Params.LikedChannelsScreenRoute)
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp)
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Subscribed Channels",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }*/
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                navController.navigate(Params.LikedSongsScreenRoute)
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp)
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Liked Songs",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                navController.navigate(Params.MyProfileScreenRoute)
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp),
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "My Profile",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    var color by remember {
                        mutableStateOf(InitialDashboardTextColor)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                            .align(Alignment.Center)
                            .alpha(1f)
                            .clickable {
                                color = OnclickDashboardTextColor
                                showProgress = true
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
                                    color = InitialDashboardTextColor
                                    showProgress = false
                                }
                                builder.show()
                            },
                        elevation = 16.dp,
                        shape = RoundedCornerShape(60.dp),
                    ) {
                        BackgroundImageCardMusic()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign Out",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                color = color
                            )
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
//            if (showProgress) {
//                ProgressIndicator()
//            }
        }
    }
}
