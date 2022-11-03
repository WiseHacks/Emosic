package com.example.emosic.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emosic.utils.Params
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.runBlocking

@Composable
fun DashBoardScreen(
    context: Context,
    navController: NavController
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "WELCOME - open camera", modifier = Modifier
                .fillMaxWidth()
                .size(70.dp)
                .clickable {
                           navController.navigate(Params.CapturePhotoScreenRoute)
                }, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val app = App.create(Params.APP_ID)
                    runBlocking {
                        app.currentUser?.logOut()
                    }
                    navController.popBackStack()
                    navController.navigate(Params.LoginScreenRoute)
                }
                , Modifier.size(90.dp)) {
                Text(text = "Log OUT", fontSize = 30.sp, color = Color.Yellow)
            }
        }
    }
}