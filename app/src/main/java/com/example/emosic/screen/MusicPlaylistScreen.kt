package com.example.emosic.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chaquo.python.Python
import com.example.emosic.data.SearchResponse
import com.example.emosic.repository.UserDataRepositoryImpl
import com.example.emosic.service.YoutubeApi
import com.example.emosic.utils.Params
import io.realm.kotlin.mongodb.App
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MusicPredictionScreen(
    context: Context,
    navController: NavController
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
            Button(onClick = {
//                val py = Python.getInstance()
//                val module = py.getModule("plot")
//                val sum = module.callAttr("plot", 1, 2)
//                Log.v("PythonCheck", sum.toString())
                YoutubeApi.apiInstance().search(searchString = query, maxResults = 50).enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        val result = response.body()?.items
                        Log.v("query_res", result.toString())
                        if (result != null) {
                            Log.v("query_res", result.size.toString())
                            UserDataRepositoryImpl().getUserData()
                                ?.let { Log.v("query_exp_changelater", it._id.toString()) }
                        }
                    }
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                })

            }) {
                Text(text = "Search")
            }
        }


    }
    //

}