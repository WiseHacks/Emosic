package com.example.emosic.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emosic.R

@Composable
fun SongCardHelper(
    key : String,
    value : String
){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.3f),
            text = key,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.kanit_regular)),
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.fillMaxWidth(1f),
            text = value,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.kanit_regular)),
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}