package blblblbl.simplelife.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(
    startOnClick: () -> Unit,
) {
    val pages = listOf(
        OnBoardingPage.Page1,
        OnBoardingPage.Page2,
        OnBoardingPage.Page3,
        OnBoardingPage.Page4,
        OnBoardingPage.Page5
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom =  20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                HorizontalPagerIndicator(
                    pagerState = pagerState
                )
                Button(
                    onClick = {
                        if (pagerState.currentPage < ONBOARDING_LAST_PAGE_INDEX) {
                            coroutineScope.launch { pagerState.animateScrollToPage(5) }
                        } else {
                            startOnClick()
                        }
                    }
                ) {
                    Text(text = if (pagerState.currentPage < ONBOARDING_LAST_PAGE_INDEX) "skip" else "finish")
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                HorizontalPager(
                    count = OnBoardingPage.ONBOARDING_PAGES_COUNT,
                    state = pagerState,
                    verticalAlignment = Alignment.Bottom
                ) { position ->
                    PagerScreen(onBoardingPage = pages[position])
                }

            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.8f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(
            text = onBoardingPage.title,
            modifier = Modifier
                .fillMaxWidth(),

            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )
    }
}


const val ONBOARDING_FIRST_PAGE_INDEX = 0
const val ONBOARDING_LAST_PAGE_INDEX = 4