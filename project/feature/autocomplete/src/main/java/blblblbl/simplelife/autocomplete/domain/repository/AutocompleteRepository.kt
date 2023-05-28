package blblblbl.simplelife.autocomplete.domain.repository

interface AutocompleteRepository {
    suspend fun searchCities(cityName:String):List<String>
}