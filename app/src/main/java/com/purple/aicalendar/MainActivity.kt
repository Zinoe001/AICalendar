package com.purple.aicalendar

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.purple.aicalendar.ui.navigations.AICalendarNavigation
import com.purple.aicalendar.ui.theme.AICalenderTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AICalenderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AICalenderContent(innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AICalenderContent(p: PaddingValues){
    val navController = rememberNavController()
    val permission  = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    SideEffect {
        permission.launchPermissionRequest()
    }

    AICalendarNavigation(
        navController=navController)
}
