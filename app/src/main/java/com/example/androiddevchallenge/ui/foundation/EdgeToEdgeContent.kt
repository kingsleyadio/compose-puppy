package com.example.androiddevchallenge.ui.foundation

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun EdgeToEdgeContent(content: @Composable () -> Unit) {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window
    val statusBarColor = MaterialTheme.colors.primary
    val statusBarAndroidColor = Color(
        statusBarColor.red,
        statusBarColor.green,
        statusBarColor.blue,
        statusBarColor.alpha
    )
    val navigationBarColor = MaterialTheme.colors.primary
    val navigationBarAndroidColor = Color(
        navigationBarColor.red,
        navigationBarColor.green,
        navigationBarColor.blue,
        navigationBarColor.alpha
    )
    window.statusBarColor = statusBarAndroidColor.toArgb()
    window.navigationBarColor = navigationBarAndroidColor.toArgb()
    val insetsController = remember(view, window) {
        WindowCompat.getInsetsController(window, view)
    }
    val isLightTheme = MaterialTheme.colors.isLight
    insetsController?.run {
        isAppearanceLightNavigationBars = isLightTheme
        isAppearanceLightStatusBars = isLightTheme
    }
    ProvideWindowInsets(content = content)
}
