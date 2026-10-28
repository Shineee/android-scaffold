package com.scaffold.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.scaffold.app.ui.navigation.AppNavHost
import com.scaffold.app.ui.settings.SettingsViewModel
import com.scaffold.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val themeMode by settingsViewModel.themeMode.collectAsState()
            AppTheme(themeMode = themeMode) {
                AppNavHost()
            }
        }
    }
}
