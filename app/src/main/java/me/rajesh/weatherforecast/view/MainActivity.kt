package me.rajesh.weatherforecast.view

import android.R.attr.text
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import me.rajesh.weatherforecast.R
import me.rajesh.weatherforecast.app.WeatherApplication
import me.rajesh.weatherforecast.data.model.WeatherItem
import me.rajesh.weatherforecast.di.component.DaggerMainActivityComponent
import me.rajesh.weatherforecast.di.component.MainActivityComponent
import me.rajesh.weatherforecast.di.module.MainActivityModule
import me.rajesh.weatherforecast.ui.theme.WeatherForecastTheme
import me.rajesh.weatherforecast.view.base.UiState
import me.rajesh.weatherforecast.view.composables.mainActivity.ErrorView
import me.rajesh.weatherforecast.view.composables.mainActivity.ForecastReportView
import me.rajesh.weatherforecast.view.composables.mainActivity.LoadingView
import me.rajesh.weatherforecast.view.composables.mainActivity.SearchScreen
import me.rajesh.weatherforecast.view.viewModel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    lateinit var mainActivityComponent: MainActivityComponent

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeToggleExample(mainActivityViewModel)
        }

    }

    private fun injectDependencies() {
        mainActivityComponent = DaggerMainActivityComponent
            .builder()
            .applicationComponent((application as WeatherApplication).applicationComponent)
            .mainActivityModule(MainActivityModule(this))
            .build()

        mainActivityComponent.inject(this)
    }


}


//@Preview(showSystemUi = true)
//@Composable
//fun MainViewPreview() {
//    MainView()
//}

@Composable
fun ThemeToggleExample(mainActivityViewModel: MainActivityViewModel) {
    val systemInDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(systemInDarkTheme) }
    val view = LocalView.current

    WeatherForecastTheme(darkTheme = isDarkTheme) {

        DisposableEffect(isDarkTheme) {
            val window = (view.context as? Activity)?.window ?: return@DisposableEffect onDispose { }

            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
            windowInsetsController.isAppearanceLightNavigationBars = !isDarkTheme

            onDispose { }
        }

        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        isDarkTheme = !isDarkTheme
                    },
                    modifier = Modifier.size(50.dp),
                ) {
                    Icon(
                        painter = painterResource(id = if (isDarkTheme) R.drawable.ic_light_mode else R.drawable.ic_dark_mode),
                        contentDescription = "Change Theme"
                    )
                }
            }
        ) { innerPadding ->
            MainView(
               innerPadding,
                mainActivityViewModel,
                isDarkTheme
            )
        }
    }

}


@Composable
fun MainView(innerPadding: PaddingValues, mainActivityViewModel: MainActivityViewModel, isDarkTheme: Boolean) {

    val cityName = mainActivityViewModel.cityName.collectAsState()
    val uiState by mainActivityViewModel.uiSTate.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var isNoData by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf(emptyList<WeatherItem>()) }
    var errorData by remember { mutableStateOf("Something went wrong !!") }
    val focusManager = LocalFocusManager.current
    val darkGradient = Brush.linearGradient(
        colors = listOf(Color.Yellow, Color.Green, Color.Magenta, Color.Red)
    )
    val lightGradient = Brush.linearGradient(
        colors = listOf(Color.Blue, Color.Magenta, Color.Red)
    )


    when (uiState) {
        is UiState.Loading -> {
            isLoading = true
            isNoData = false
        }

        is UiState.Success -> {
            isNoData = false
            isLoading = false
            data = (uiState as UiState.Success<List<WeatherItem>>).data
        }

        is UiState.Error -> {
            isNoData = false
            isLoading = false
            errorData = (uiState as UiState.Error).message
        }

        is UiState.NoData -> {
            isLoading = false
            isNoData = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        focusManager.clearFocus()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Weather",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                brush = if (isDarkTheme) {
                    darkGradient
                } else {
                    lightGradient
                }

            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                contentDescription = "location",
                imageVector = Icons.Default.LocationOn,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = cityName.value,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        SearchScreen(mainActivityViewModel)
        if (isLoading)
            LoadingView()
        if (!isLoading && uiState is UiState.Success)
            ForecastReportView(data)
        if(!isLoading && uiState is UiState.NoData) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.no_record),
                    contentDescription = "No record",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No Report Found.",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Check internet connectivity and search again.",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

        }
        if (!isLoading && uiState is UiState.Error)
            ErrorView(errorData) {
                mainActivityViewModel.triggerFetchCityData(cityName.value)
            }
    }

}




