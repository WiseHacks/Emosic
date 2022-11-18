package com.example.emosic.utils.backgrounds

import android.view.WindowInsets
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.emosic.R

@Composable
fun BackgroundImageGuitarBW(){
    Image(
        painter = painterResource(id = R.drawable.music_background3),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(1f),
        alpha = 0.8f
    )
}