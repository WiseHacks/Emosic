package com.example.emosic.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.emosic.data.FirestoreResponse
import com.example.emosic.repository.LikedSongsRepositoryImpl
import com.example.emosic.repository.SongIdsResponse
import com.example.emosic.utils.ProgressIndicator
import com.example.emosic.viewmodel.LikedSongsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LikedSongsScreen(
    context: Context,
    navController: NavController,
    viewModel: LikedSongsViewModel = hiltViewModel()
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (val SongIdsResponse = viewModel.songIdsResponse) {
            is FirestoreResponse.Loading -> ProgressIndicator()
            is FirestoreResponse.Success -> println("Data loading succeeded $SongIdsResponse")
            is FirestoreResponse.Failure -> println("Data loading failed")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }

}