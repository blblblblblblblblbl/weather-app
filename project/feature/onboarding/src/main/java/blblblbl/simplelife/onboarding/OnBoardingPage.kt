package blblblbl.simplelife.onboarding

import androidx.annotation.DrawableRes

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object Page1 : OnBoardingPage(
        image = R.drawable.onboarding_main,
        title = "page1_title",
        description = "page1_desc"
    )

    object Page2 : OnBoardingPage(
        image = R.drawable.onboarding_main_detailed,
        title = "page2_title",
        description = "page2_desc"
    )

    object Page3 : OnBoardingPage(
        image = R.drawable.onboarding_favourites,
        title = "page3_title",
        description = "page3_desc"
    )
    object Page4 : OnBoardingPage(
        image = R.drawable.onboarding_settings,
        title = "page4_title",
        description = "page4_desc"
    )
    object Page5 : OnBoardingPage(
        image = R.drawable.onboarding_widgets,
        title = "page5_title",
        description = "page5_desc"
    )

    companion object{
        const val ONBOARDING_PAGES_COUNT = 5
    }
}
