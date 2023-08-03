package blblblbl.simplelife.onboarding.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * @author Kirill Tolmachev 03.08.2023
 */
data class OnBoardingPage(
    @DrawableRes val imageResId: Int,
    @StringRes val titleTextResId: Int,
    @StringRes val descriptionTextResId: Int
)