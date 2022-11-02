package com.example.emosic

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emosic.utils.Params
import com.example.emosic.viewModel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        setContent {
            var startDestination by remember {
                mutableStateOf(Params.LoginScreenRoute)
            }
            val navController = rememberNavController()
            if(data != null){
                startDestination = Params.ResetPasswordScreenRoute
            }
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = Params.LoginScreenRoute) {
                    LoginScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.RegisterScreenRoute){
                    RegisterUserScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.DashBoardScreenRoute){
                    DashBoardScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.ForgotPasswordScreenRoute){
                    ForgotPasswordScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.ResetPasswordScreenRoute){
                    if (data != null) {
                        ResetPasswordScreen(
                            context = this@MainActivity,
                            navController = navController,
                            data = data
                        )
                    }
                }
            }
        }

    }
}
