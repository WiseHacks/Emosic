package com.example.emosic.screen

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.get
import androidx.navigation.NavController
import com.example.emosic.data.APIResponse
import com.example.emosic.data.Image_emosic
import com.example.emosic.data.URI_list
import com.example.emosic.data.User
import com.example.emosic.service.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MusicPredictionScreen(
    context: Context,
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Current mood", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(30.dp))
            var query by remember {
                mutableStateOf("")
            }
            OutlinedTextField(value = query, onValueChange = {
                query = it
            })
            Spacer(modifier = Modifier.height(30.dp))
            var user: User? by remember {
                mutableStateOf(null)
            }
            Button(onClick = {
                val arg2 = URI_list(listOf("0UaMYEvWZi0ZqiDOoHU3YI"))
//                val arg1 = Image_emosic(image) // this will be url

//                API.apiInstance().getRecommendations(arg2).enqueue(object : Callback<APIResponse> {
//                    override fun onResponse(
//                        call: Call<APIResponse>,
//                        response: Response<APIResponse>
//                    ) {
//                        val result = response.body()?.items
//                        Log.v("query_res", result.toString())
//                        Toast.makeText(context, "Got result", Toast.LENGTH_SHORT).show()
//                        if (result != null) {
//                            Log.v("query_res", result.toString())
//                        }
//                    }
//                    override fun onFailure(call: Call<APIResponse>, t: Throwable) {
//                        println(t)
//                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
//                    }
//                })

//                API.apiInstance().getEmotion(arg1).enqueue(object : Callback<APIResponse> {
//                    override fun onResponse(
//                        call: Call<APIResponse>,
//                        response: Response<APIResponse>
//                    ) {
//                        val result = response.body()?.items
//                        Log.v("query_res", result.toString())
//                        Toast.makeText(context, "Got result", Toast.LENGTH_SHORT).show()
//                        if (result != null) {
//                            Log.v("query_res", result.toString())
//                        }
//                    }
//                    override fun onFailure(call: Call<APIResponse>, t: Throwable) {
//                        println(t)
//                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
//                    }
//                })

            }) {
                Text(text = "RUN")
            }
        }


    }
    //

}