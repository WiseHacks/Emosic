package com.example.emosic.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.emosic.data.APIResponse
import com.example.emosic.data.FirestoreResponse
import com.example.emosic.data.Song
import com.example.emosic.data.URI_list
import com.example.emosic.repository.SongIds
import com.example.emosic.service.API
import com.example.emosic.utils.Params
import com.example.emosic.utils.ProgressIndicator
import com.example.emosic.utils.SongCard
import com.example.emosic.utils.SongCard_
import com.example.emosic.utils.backgrounds.BackgroundImageGuitarBW
import com.example.emosic.viewmodel.LikedSongsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecommendationsScreen(
    context: Context,
    navController: NavController,
    viewModel: LikedSongsViewModel = hiltViewModel()
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val db = FirebaseFirestore.getInstance()
        var showProgress by remember{
            mutableStateOf(true)
        }
        var songIds : SongIds? by remember {
            mutableStateOf(null)
        }
        db.collection("User").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).
            get().addOnCompleteListener {
                if(it.isSuccessful){
                    showProgress = false
                    songIds = it.result.get("likedSongs") as SongIds
                }
                else{
                    navController.popBackStack()
                    navController.navigate(Params.DashBoardScreenRoute)
                }
        }
        if(songIds != null){
            RecommendationsList(
                context = context,
                navController = navController,
                songIdsResponse = songIds,
                addSong = {songId ->
                    viewModel.addSong(FirebaseAuth.getInstance().currentUser?.uid.toString(), songId)
                },
            )
        }
        if(showProgress) ProgressIndicator()
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecommendationsList(
    context: Context,
    navController: NavController,
    songIdsResponse: SongIds?,
    addSong:(songId : String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BackgroundImageGuitarBW()
        var showProgress by remember{
            mutableStateOf(true)
        }
        if(songIdsResponse == null){
            navController.popBackStack()
            navController.navigate(Params.DashBoardScreenRoute)
        }
        val args = songIdsResponse?.let { URI_list(it) }
        var result : List<APIResponse.Item>? by remember {
            mutableStateOf(null)
        }
        LaunchedEffect(key1 = args!=null){
            if (args != null) {
                API.apiInstance().getRecommendations(args).enqueue(object : Callback<APIResponse> {
                    override fun onResponse(
                        call: Call<APIResponse>,
                        response: Response<APIResponse>
                    ) {
                        showProgress = false
                        result = response.body()?.items
                        Toast.makeText(context, "Results fetched successfully", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                        navController.navigate(Params.DashBoardScreenRoute){
                            popUpTo(0)
                        }
                    }
                })
            }
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
                if(result != null){
                    items(result!!) { item ->
//                        println(item.toString())
                        SongCard_(context = context, song = item, addSong = addSong)
                    }
                }
            }
        }
        if(showProgress) ProgressIndicator()
    }
}

