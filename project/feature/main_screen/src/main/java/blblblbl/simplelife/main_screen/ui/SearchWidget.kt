package blblblbl.simplelife.main_screen.ui

import android.Manifest
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClearClicked: () -> Unit,
    onLocationClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "SearchWidget"
            }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape),
        color = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "TextField"
                },
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = 0.5f),
                    text = "search here"
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "CloseButton"
                        },
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onClearClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            trailingIcon = {
                Row() {
                    LocationButton {onLocationClicked()}
                    IconButton(
                        modifier = Modifier,
                        onClick = {onSearchClicked(text)}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            )
        )
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationButton(
    onClick: () -> Unit
){
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onPermissionsResult = {
            val notGrantedList = it.values.filter {!it}
            if (notGrantedList.isEmpty()){
                onClick()
            }
        }
    )
    IconButton(onClick = {
        if (locationPermissionState.allPermissionsGranted){
            onClick()
        }
        else{
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }) {
        Icon(Icons.Default.MyLocation, contentDescription = "location button")
    }
}