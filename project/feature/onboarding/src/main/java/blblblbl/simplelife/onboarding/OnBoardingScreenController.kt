package blblblbl.simplelife.onboarding

import androidx.compose.runtime.Composable

/**
 * @author Kirill Tolmachev 03.08.2023
 */

@Composable
fun OnBoardingScreenController(
    lastPageButtonOnClick: () -> Unit
) {
    OnBoardingScreenView {
        lastPageButtonOnClick()
    }
}