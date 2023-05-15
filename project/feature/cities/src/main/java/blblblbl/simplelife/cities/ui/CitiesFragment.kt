package blblblbl.simplelife.cities.ui

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import blblblbl.simplelife.cities.R
import blblblbl.simplelife.cities.domain.model.ForecastResponse
import blblblbl.simplelife.cities.presentation.CitiesFragmentViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow

@Composable
fun CitiesFragment() {
    val viewModel = hiltViewModel<CitiesFragmentViewModel>()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        CitiesGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            cities = viewModel.pagedCities,
            cityOnClick = {},
            removeOnClick = {name->
                viewModel.removeCity(name)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CitiesGrid(
    modifier: Modifier = Modifier,
    cities: Flow<PagingData<ForecastResponse>>,
    cityOnClick: () -> Unit,
    removeOnClick: (String) -> Unit
) {
    val lazyCitiesItems: LazyPagingItems<ForecastResponse> = cities.collectAsLazyPagingItems()
    val listStaggeredState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = listStaggeredState,
        verticalItemSpacing = 6.dp,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(lazyCitiesItems) { item ->
            item?.let { CityElement(forecast = item, onClick = { cityOnClick() }, refreshOnClick = {}, removeOnClick = removeOnClick) }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityElement(
    modifier: Modifier = Modifier,
    forecast: ForecastResponse,
    onClick: () -> Unit,
    refreshOnClick: () -> Unit,
    removeOnClick: (String) -> Unit
) {
    var expandMenu by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.combinedClickable(
            onClick = { onClick() },
            onLongClick = { expandMenu = true }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            forecast.location?.name?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                forecast.current?.tempC?.let {
                    Text("${it}°C", style = MaterialTheme.typography.headlineLarge)
                }
                forecast.current?.condition?.icon?.let {
                    GlideImage(
                        imageModel = { "https:" + it },
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            forecast.current?.condition?.text?.let {
                Text(it)
            }
            forecast.current?.windKph?.let { windSpeed ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(R.drawable.wind_icon),
                        contentDescription = "wind"
                    )
                    Text(text = "${windSpeed} km/h")
                }
            }
            forecast.current?.lastUpdated?.let { lastUpdated ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "last updated", style = MaterialTheme.typography.bodyMedium)
                        Text(text = lastUpdated, style = MaterialTheme.typography.bodyMedium)
                    }
                    IconButton(onClick = { refreshOnClick() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "refresh",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
        forecast.location?.name?.let{name->
            DropdownMenu(expanded = expandMenu, onDismissRequest = { expandMenu = false }) {
                DropdownMenuItem(
                    text = { Text(text = "remove") },
                    onClick = {
                        expandMenu = false
                        removeOnClick(name)
                    }
                )
            }
        }

    }
}

@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
public fun <T : Any> LazyStaggeredGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyStaggeredGridScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}