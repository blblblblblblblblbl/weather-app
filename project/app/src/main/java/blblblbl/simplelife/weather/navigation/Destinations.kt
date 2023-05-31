package blblblbl.simplelife.weather.navigation

interface AppDestination {
    val name:String
    val route: String
}
object MainDest:AppDestination{
    override val name: String = "main_dest_name"
    override val route: String = "mainDest"
}
object CitiesDest:AppDestination{
    override val name: String = "cities_dest_name"
    override val route: String = "citiesDest"
}
object AppSettingDest:AppDestination{
    override val name: String = "settings_dest_name"
    override val route: String = "appSettingDest"
}
object OnBoardingDest:AppDestination{
    override val name: String = "onboarding_dest_name"
    override val route: String = "onBoardingDest"
}
object AuthorsDest:AppDestination{
    override val name: String = "authors_dest_name"
    override val route: String = "authorsDest"
}
val menuDestinations = listOf<AppDestination>(MainDest,CitiesDest,AppSettingDest,OnBoardingDest,AuthorsDest)