package me.rajesh.weatherforecast.view.composables.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rajesh.weatherforecast.R
import me.rajesh.weatherforecast.data.model.WeatherItem
import me.rajesh.weatherforecast.ui.theme.LocalCardColors
import me.rajesh.weatherforecast.utils.getDayOfWeekFromDate

@Composable
fun ForecastReportView(data: List<WeatherItem>) {
    LazyColumn(

    ) {

        item {

            data[0]?.let {
                WeatherCard(
                    temperature = data[0].main.temp,
                    condition = data[0].weather[0].description,
                    day = "Today",
                    iconCode = data[0].weather[0].icon

                )
            }

        }
        item {
            data[1]?.let {
                WeatherCard(
                    temperature = data[1].main.temp,
                    condition = data[1].weather[0].description,
                    day = "Tomorrow",
                    iconCode = data[1].weather[0].icon
                )
            }

        }
        item {
            data[2]?.let {
                WeatherCard(
                    temperature = data[2].main.temp,
                    condition = data[2].weather[0].description,
                    day = getDayOfWeekFromDate(data[2].dt_txt),
                    iconCode = data[2].weather[0].icon
                )
            }

        }


    }
}

@Composable
fun WeatherCard(
    temperature: Double,
    condition: String,
    day: String = "Today",
    iconCode: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = LocalCardColors.current.background)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2196F3).copy(0.5f),
                                Color(0xFF64B5F6).copy(0.5f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {

                WeatherIconWithGlide(
                    iconCode = iconCode
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$temperature",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "°C",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }
                Text(
                    text = condition,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

@Composable
fun WeatherIconWithGlide(iconCode: String, modifier: Modifier = Modifier) {
    val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

    AsyncImage(
        model = iconUrl,
        contentDescription = "Weather Icon",
        contentScale = ContentScale.Fit,
        modifier = modifier.size(60.dp),
        placeholder = painterResource(R.drawable.ic_error)
    )
}

