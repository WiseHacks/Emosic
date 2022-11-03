package com.example.emosic

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emosic.utils.Params
import com.example.emosic.screen.*
import io.realm.kotlin.mongodb.App
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.suspendCoroutine

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
        val action: String? = intent?.action
        val data: Uri? = intent?.data
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            var startDestination by remember {
                mutableStateOf(Params.LoginScreenRoute)
            }
            val navController = rememberNavController()
            if(data != null){
                startDestination = Params.ResetPasswordScreenRoute
            }
            else{
                Log.v("DEbug", "debug")
            }
            val app = App.create(Params.APP_ID)
            if(app.currentUser?.loggedIn == true){
                startDestination = Params.DashBoardScreenRoute
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
                composable(route = Params.CapturePhotoScreenRoute){
                    if(shouldShowCamera.value) {
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
                        if (shouldShowPhoto.value) {
                            Log.e("emosic", photoUri.toString())
//                            Image(
//                                painter = rememberImagePainter(photoUri),
//                                contentDescription = null,
//                                modifier = Modifier.fillMaxSize()
//                            )
                        }
                    }
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
