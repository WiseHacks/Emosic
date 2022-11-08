package com.example.emosic.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.emosic.R

@Composable
fun WelcomeImage(height: Dp = 350.dp){
    Box(modifier = Modifier.height(height), contentAlignment = Alignment.Center) {
        // CHANGE HEIGHT - MAYBE 0.25f
        Image(
            painter = painterResource(id = R.drawable.emosic_welcome),
            contentDescription = "Welcome Icon",
            Modifier.size(250.dp)
        )
    }
}