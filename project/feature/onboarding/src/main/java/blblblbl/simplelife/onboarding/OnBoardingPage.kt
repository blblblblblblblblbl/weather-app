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
        title = "Main screen",
        description = "search city by name or geo, look at current weather, add city to favourites"
    )

    object Page2 : OnBoardingPage(
        image = R.drawable.onboarding_main_detailed,
        title = "Forecast",
        description = "look at detailed forecast"
    )

    object Page3 : OnBoardingPage(
        image = R.drawable.onboarding_favourites,
        title = "Favourites",
        description = "look at all favourite cities in one screen, remove them by long tap, add them on home screen widget from home screen"
    )
    object Page4 : OnBoardingPage(
        image = R.drawable.onboarding_settings,
        title = "Settings",
        description = "configure app with your preference, pick theme and weather units "
    )
    object Page5 : OnBoardingPage(
        image = R.drawable.onboarding_widgets,
        title = "Widgets",
        description = "add widgets to home screen, there are 3 sizes"
    )

    companion object{
        const val ONBOARDING_PAGES_COUNT = 5
    }
}
