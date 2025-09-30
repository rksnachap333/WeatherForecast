package me.rajesh.weatherforecast.view.composables.mainActivity

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import me.rajesh.weatherforecast.utils.isNetworkAvailable
import me.rajesh.weatherforecast.view.viewModel.MainActivityViewModel

@Composable
fun SearchScreen(
    mainActivityViewModel: MainActivityViewModel
) {
    var query by remember { mutableStateOf(mainActivityViewModel.cityName.value) }
    val context = LocalContext.current

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearchClick = {
            val isConnected = isNetworkAvailable(context)
            if (isConnected) {
                mainActivityViewModel.triggerFetchCityData(query)
                println("Searching for: $query")
            } else {
                Toast.makeText(context, "No internet connection!!", Toast.LENGTH_SHORT).show()
            }
        }
    )

}


@Composable
fun SearchBar(
    query: String = "",
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    "Search...",
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .focusRequester(focusRequester)
                .onFocusChanged({ isFocused = it.isFocused }),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                color = when {
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> Color.Gray.copy(0.6f)
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceTint,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceTint,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                focusManager.clearFocus()
                onSearchClick()
            },
            modifier = Modifier
                .size(56.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}

