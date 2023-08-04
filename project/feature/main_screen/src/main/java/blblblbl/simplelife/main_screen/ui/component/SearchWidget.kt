package blblblbl.simplelife.main_screen.ui.component

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import blblblbl.simplelife.main_screen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onLocationClick: () -> Unit,
    onCityClick: (String) -> Unit,
    suggestedVariants:List<String>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.extraLarge
            ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(modifier.fillMaxWidth()) {
            val focusUtils = rememberFocusUtils()
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = { Text(modifier = Modifier.alpha(alpha = 0.5f), text = stringResource(id = R.string.search_here)) },
                singleLine = true,
                leadingIcon = { LeadingButton(onClearClick) },
                trailingIcon = {
                    TrailingButtons(
                        onLocationClick = {
                            onLocationClick()
                            focusUtils.clearFocus()
                        },
                        onSearchClick = {
                            onSearchClick()
                            focusUtils.clearFocus()
                        }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent)
            )
            AnimatedVisibility(suggestedVariants.isNotEmpty() && focusUtils.isKeyboardVisible.value){
                AutoCompleteCities(
                    cities = suggestedVariants,
                    onClick = { city->
                        onCityClick(city)
                        focusUtils.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                )
            }
        }
    }
}

class FocusUtils(
    var isKeyboardVisible: MutableState<Boolean>,
    val clearFocus: () -> Unit
)
@Composable
private fun rememberFocusUtils() : FocusUtils {
    var isKeyboardVisible = remember { mutableStateOf(false) }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    val focusManager = LocalFocusManager.current
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            isKeyboardVisible.value = isKeyboardOpen
            if (!isKeyboardOpen) focusManager.clearFocus()
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return remember {
        FocusUtils(
            isKeyboardVisible = isKeyboardVisible,
            clearFocus = { focusManager.clearFocus() }
        )
    }
}

@Composable
private fun LeadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Icon",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TrailingButtons(
    onLocationClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        IconButton(onClick = onLocationClick) {
            Icon(Icons.Default.MyLocation, contentDescription = "location button")
        }
        IconButton(onClick = onSearchClick
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AutoCompleteCities(
    cities: List<String>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ){
        items(cities){item->
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(item) }
            ) {
                Text(modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp), text = item)
            }
        }
    }
}

@Preview(
    backgroundColor = 0xFF_FFFFFF,
    showBackground = true
)
@Composable
private fun LeadingButtonPreview() {
    LeadingButton(onClick = { /*TODO*/ })
}

@Preview(
    backgroundColor = 0xFF_FFFFFF,
    showBackground = true
)
@Composable
private fun TrailingButtonsPreview() {
    TrailingButtons(onLocationClick = { /*TODO*/ }, onSearchClick = { /*TODO*/ })
}

@Preview(
    backgroundColor = 0xFF_FFFFFF,
    showBackground = true
)
@Composable
private fun AutoCompleteCitiesPreview() {
    val cities = listOf("city1", "city2", "city3", "city4")
    AutoCompleteCities(cities = cities, onClick = {})
}

@Preview(
    backgroundColor = 0xFF_FFFFFF,
    showBackground = true
)
@Composable
private fun Preview(){
    var text by remember { mutableStateOf("") }
    val cities = listOf("city1", "city2", "city3", "city4")
    SearchWidget(
        text = text,
        onTextChange = { text = it },
        onSearchClick = {},
        onClearClick = { text = "" },
        onLocationClick = {},
        suggestedVariants = if (text.isNotEmpty()) cities else listOf(),
        onCityClick = { text = it }
    )
}