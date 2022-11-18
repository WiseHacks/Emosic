package com.example.emosic.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emosic.data.Song
import com.example.emosic.utils.SongCard

@Composable
fun LikedChannelsScreen(
    context: Context,
    navController: NavController
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Spacer(modifier = Modifier.height(200.dp))
        Text("My Liked Channels", textAlign = TextAlign.Center, fontSize = 30.sp)
    }
}