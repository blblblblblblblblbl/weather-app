package blblblbl.simplelife.main_screen.ui

import android.Manifest
import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClearClicked: () -> Unit,
    onLocationClicked: () -> Unit,
    suggestedVariants:List<String>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "SearchWidget"
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.extraLarge
            ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {



        Column() {
            var isKeyboardVisible by remember { mutableStateOf(false) }
            val view = LocalView.current
            val viewTreeObserver = view.viewTreeObserver
            val focusManager = LocalFocusManager.current
            DisposableEffect(viewTreeObserver) {
                val listener = ViewTreeObserver.OnGlobalLayoutListener {
                    val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
                    isKeyboardVisible = isKeyboardOpen
                    if (!isKeyboardOpen) focusManager.clearFocus()
                }

                viewTreeObserver.addOnGlobalLayoutListener(listener)
                onDispose {
                    viewTreeObserver.removeOnGlobalLayoutListener(listener)
                }
            }
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
                            onClearClicked()
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
                        LocationButton {
                            onLocationClicked()
                            focusManager.clearFocus()
                        }
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                onSearchClicked(text)
                                focusManager.clearFocus()
                            }
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
                ),
                colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent)
            )
            AnimatedVisibility(suggestedVariants.isNotEmpty()&&isKeyboardVisible){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ){
                    items(suggestedVariants){item->
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onTextChange(item)
                                    onSearchClicked(item)
                                    focusManager.clearFocus()
                                }
                        ) {
                            Text(modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp),text = item)
                        }
                    }
                }
            }
        }

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