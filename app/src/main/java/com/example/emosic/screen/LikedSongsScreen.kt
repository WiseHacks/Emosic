package com.example.emosic.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.data.FirestoreResponse
import com.example.emosic.data.Song
import com.example.emosic.repository.SongIds
import com.example.emosic.repository.SongIdsResponse
import com.example.emosic.utils.BackgroundImageApe
import com.example.emosic.utils.BackgroundImageGuitar
import com.example.emosic.utils.ProgressIndicator
import com.example.emosic.utils.SongCard
import com.example.emosic.utils.backgrounds.BackgroundImageGuitarBW
import com.example.emosic.viewmodel.LikedSongsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

@Composable
fun LikedSongsScreen(
    context: Context,
    navController: NavController,
    viewModel: LikedSongsViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (val SongIdsResponse = viewModel.songIdsResponse) {
            is FirestoreResponse.Loading -> ProgressIndicator()
            is FirestoreResponse.Success -> {
//                println("Data loading success $SongIdsResponse")
                SongsList(
                    songIdsResponse = SongIdsResponse.data,
                    removeSong = {songId ->
                        viewModel.deleteSong(FirebaseAuth.getInstance().currentUser?.uid.toString(), songId)
                    },
                )
            }
            is FirestoreResponse.Failure -> {
//                println("Data loading failed")
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SongsList(
    songIdsResponse: SongIds,
    removeSong:(songId : String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BackgroundImageGuitarBW()
        var showProgress by remember{
            mutableStateOf(true)
        }
        Card(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 80.dp)
            .alpha(0.9f),
            elevation = 10.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {
                items(songIdsResponse) { songId ->
                    var song : Song? by remember {
                        mutableStateOf(null)
                    }
                    // A warning here - ignore
                    CoroutineScope(Dispatchers.IO).launch{
                        val document = getSong(songId)?.documents
                        if (document != null) {
                            if(document.size > 0) {
                                song = getSong(songId)?.documents?.get(0)?.toObject(Song::class.java)
                                showProgress = false
                            }
                        }
                    }
                    if(!showProgress && song != null){
                        SongCard(song = song, removeSong)
                    }
                }
            }
        }
        if(showProgress) ProgressIndicator()
    }
}

suspend fun getSong(songId: String): QuerySnapshot? {
    val db = FirebaseFirestore.getInstance()
    return try{
        db.collection("Song").whereEqualTo("id", songId).get().await()
    }catch (e:Exception){
        null
    }
}