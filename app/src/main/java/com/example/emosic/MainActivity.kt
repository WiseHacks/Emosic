package com.example.emosic

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emosic.utils.Params
import com.example.emosic.screen.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    Camera things...
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private fun handleImageCapture(uri: Uri) {
        Log.i("emosic_cam", "Image captured: $uri")
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    //    Camera things...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            var startDestination by remember {
                mutableStateOf(Params.LoginScreenRoute)
            }
            val navController = rememberNavController()
            if (FirebaseAuth.getInstance().currentUser != null) {
                startDestination = Params.DashBoardScreenRoute
            }
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = Params.LoginScreenRoute) {
                    LoginScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.RegisterScreenRoute) {
                    RegisterUserScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.DashBoardScreenRoute) {
                    DashBoardScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.ForgotPasswordScreenRoute) {
                    ForgotPasswordScreen(context = this@MainActivity, navController = navController)
                }
                composable(route = Params.CapturePhotoScreenRoute) {
                    requestCameraPermission()
                    outputDirectory = getOutputDirectory()
                    cameraExecutor = Executors.newSingleThreadExecutor()
                    if (shouldShowCamera.value) {
                        CapturePhotoScreenView(
                            context = this@MainActivity,
                            navController = navController,
                            outputDirectory = outputDirectory,
                            executor = cameraExecutor,
                            onImageCaptured = ::handleImageCapture,
                            onError = { Log.e("emosic_cam", "View error:", it) }
                        )
                    }
                    if (shouldShowPhoto.value) {
                        Log.e("emosic_cam", photoUri.toString())
                        navController.popBackStack()
                        navController.navigate(Params.MusicPredictionScreenRoute)
                        shouldShowPhoto.value = false
                    }
                }
                composable(route = Params.MusicPredictionScreenRoute) {
                    MusicPredictionScreen(
                        context = this@MainActivity,
                        navController = navController
                    )
                }
                composable(route = Params.MyProfileScreenRoute) {
                    MyProfileScreen(
                        context = this@MainActivity,
                        navController = navController
                    )
                }
                composable(route = Params.LikedSongsScreenRoute) {
                    LikedSongsScreen(
                        context = this@MainActivity,
                        navController = navController
                    )
                }
                composable(route = Params.LikedChannelsScreenRoute) {
                    LikedChannelsScreen(
                        context = this@MainActivity,
                        navController = navController
                    )
                }
                composable(route = Params.RecommendationsScreenRoute) {
                    RecommendationsScreen(
                        context = this@MainActivity,
                        navController = navController
                    )
                }
            }
        }
//        Camera things
        requestCameraPermission()
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
//        Camera things
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("emosic", "Permission granted")
            shouldShowCamera.value = true
        } else {
            Log.i("emosic", "Permission denied")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("emosic", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("emosic", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

}
