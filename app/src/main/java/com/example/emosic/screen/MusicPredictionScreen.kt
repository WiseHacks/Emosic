package com.example.emosic.screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.get
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.emosic.R
import com.example.emosic.data.*
import com.example.emosic.service.API
import com.example.emosic.utils.Params
import com.example.emosic.utils.ProgressIndicator
import com.example.emosic.utils.SongCard_
import com.example.emosic.utils.backgrounds.BackgroundImageGuitarBW
import com.example.emosic.viewmodel.LikedSongsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MusicPredictionScreen(
    context: Context,
    navController: NavController,
    photoUri: Uri,
    viewModel: LikedSongsViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
//        BackgroundImageGuitarBW()
        var showProgress by remember {
            mutableStateOf(true)
        }
        var downloadUrl: String? by remember {
            mutableStateOf(null)
        }
        LaunchedEffect(key1 = showProgress) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imgRef =
                storageRef.child("images/" + FirebaseAuth.getInstance().currentUser?.uid.toString() + ".jpg")
            imgRef.putFile(photoUri).continueWith { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(Params.DashBoardScreenRoute)
                }
                imgRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result!!.addOnSuccessListener { it ->
                        downloadUrl = it.toString()
                        println(downloadUrl)
                        showProgress = false
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(Params.DashBoardScreenRoute)
                }
            }
        }
        if (showProgress) ProgressIndicator()
        if (!showProgress) {
            RecommendationsListWithEmotion(
                context = context,
                navController = navController,
                downloadUrl = downloadUrl,
                addSong = { songId ->
                    viewModel.addSong(
                        FirebaseAuth.getInstance().currentUser?.uid.toString(),
                        songId
                    )
                },
            )
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecommendationsListWithEmotion(
    context: Context,
    navController: NavController,
    downloadUrl: String?,
    addSong: (songId: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BackgroundImageGuitarBW()
        var showProgress by remember {
            mutableStateOf(true)
        }
        if (downloadUrl == null) {
            navController.popBackStack()
            navController.navigate(Params.DashBoardScreenRoute)
        }
        val args = downloadUrl?.let { Image_emosic(it) }
        var result: List<APIResponseWithEmotion.Item>? by remember {
            mutableStateOf(null)
        }
        var emotion by remember {
            mutableStateOf("")
        }
        var mood by remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = args != null) {
            if (args != null) {
                API.apiInstance().getRecommendationsWithEmotion(args)
                    .enqueue(object : Callback<APIResponseWithEmotion> {
                        override fun onResponse(
                            call: Call<APIResponseWithEmotion>,
                            response: Response<APIResponseWithEmotion>
                        ) {
                            showProgress = false
                            result = response.body()?.items
                            emotion = response.body()?.emotion.toString()
                            mood = response.body()?.mood.toString()
                            Toast.makeText(
                                context,
                                "Results fetched successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<APIResponseWithEmotion>, t: Throwable) {
                            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                            navController.navigate(Params.DashBoardScreenRoute) {
                                popUpTo(0)
                            }
                        }
                    })
            }
        }
        Card(
            modifier = Modifier
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
                if (result != null) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Emotion - $emotion",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                color = Color.Red
                            )
                            Text(
                                text = "Mood - $mood",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                color = Color.Red
                            )
                        }
                    }
                    items(result!!) { item ->
//                        println(item.toString())
                        val song = Song(
                            item.id,
                            item.artist_name,
                            item.artist_uri,
                            item.track_name,
                            item.track_uri,
                            item.duration_ms,
                            item.album_name,
                            item.album_uri,
                            item.genres
                        )
                        SongCard_(
                            context = context,
                            song = song,
                            addSong = addSong
                        )
                    }
                }
            }
        }
        if (showProgress) ProgressIndicator()
    }
}
