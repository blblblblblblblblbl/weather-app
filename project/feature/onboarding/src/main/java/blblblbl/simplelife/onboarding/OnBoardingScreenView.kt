package blblblbl.simplelife.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.onboarding.component.BottomBar
import blblblbl.simplelife.onboarding.component.OnBoardingPage
import blblblbl.simplelife.onboarding.component.PagerScreen
import kotlinx.coroutines.launch

/**
 * @author Kirill Tolmachev 03.08.2023
 */

val onBoardingPages = listOf(
    OnBoardingPage(
        imageResId = R.drawable.onboarding_page1_img,
        titleTextResId = R.string.page1_title,
        descriptionTextResId = R.string.page1_desc
    ),
    OnBoardingPage(
        imageResId = R.drawable.onboarding_page2_img,
        titleTextResId = R.string.page2_title,
        descriptionTextResId = R.string.page2_desc
    ),
    OnBoardingPage(
        imageResId = R.drawable.onboarding_page3_img,
        titleTextResId = R.string.page3_title,
        descriptionTextResId = R.string.page3_desc
    ),
    OnBoardingPage(
        imageResId = R.drawable.onboarding_page4_img,
        titleTextResId = R.string.page4_title,
        descriptionTextResId = R.string.page4_desc
    ),
    OnBoardingPage(
        imageResId = R.drawable.onboarding_page5_img,
        titleTextResId = R.string.page5_title,
        descriptionTextResId = R.string.page5_desc
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreenView(
    lastPageButtonOnClick: () -> Unit,
) {
    val pagerState = rememberPagerState()
    Scaffold(
        bottomBar = {
            BottomBar(
                pagerState = pagerState,
                pageCount = onBoardingPages.size,
                lastPageButtonOnClick = lastPageButtonOnClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )
        }
    ) {
        HorizontalPager(
            pageCount = onBoardingPages.size,
            state = pagerState,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { position ->
            PagerScreen(
                onBoardingPage = onBoardingPages[position],
                modifier = Modifier.fillMaxWidth()
            )
        }
        val coroutineScope = rememberCoroutineScope()
        if (pagerState.currentPage > 0) {
            BackHandler {
                coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    OnBoardingScreenView {}
}