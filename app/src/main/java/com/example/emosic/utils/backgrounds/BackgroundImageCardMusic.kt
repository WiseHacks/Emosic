package com.example.emosic.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.emosic.R


@Composable
fun BackgroundImageCardMusic(){
    Image(
        painter = painterResource(id = R.drawable.card_bg1),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(1f),
        alpha = 0.7f
    )
}