package blblblbl.simplelife.onboarding.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.onboarding.R
import kotlinx.coroutines.launch

/**
 * @author Kirill Tolmachev 03.08.2023
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomBar(
    pagerState: PagerState,
    pageCount: Int,
    lastPageButtonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (pagerState.currentPage + 1 < pageCount) {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    lastPageButtonOnClick()
                }
            }
        ) {
            Text(text = stringResource(id = R.string.next))
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = pageCount,
            activeColor = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun Preview() {
    val pagerState = rememberPagerState()
    BottomBar(
        pagerState = pagerState,
        pageCount = 3,
        lastPageButtonOnClick = { }
    )
}


