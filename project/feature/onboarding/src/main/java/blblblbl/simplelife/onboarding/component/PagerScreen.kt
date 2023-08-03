package blblblbl.simplelife.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.onboarding.onBoardingPages

/**
 * @author Kirill Tolmachev 03.08.2023
 */

@Composable
fun PagerScreen(
    modifier: Modifier = Modifier,
    onBoardingPage: OnBoardingPage
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.imageResId),
            contentDescription = stringResource(id = onBoardingPage.titleTextResId)
        )
        Text(
            text = stringResource(id = onBoardingPage.titleTextResId),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = onBoardingPage.descriptionTextResId),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun PreviewPage1() {
    PagerScreen(onBoardingPage = onBoardingPages[0])
}

@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun PreviewPage2() {
    PagerScreen(onBoardingPage = onBoardingPages[1])
}

@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun PreviewPage3() {
    PagerScreen(onBoardingPage = onBoardingPages[2])
}

@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun PreviewPage4() {
    PagerScreen(onBoardingPage = onBoardingPages[3])
}

@Preview(
    backgroundColor = 0xFF_FFFF,
    showBackground = true
)
@Composable
private fun PreviewPage5() {
    PagerScreen(onBoardingPage = onBoardingPages[4])
}