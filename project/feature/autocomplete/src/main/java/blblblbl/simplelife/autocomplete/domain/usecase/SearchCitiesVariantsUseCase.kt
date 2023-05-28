package blblblbl.simplelife.autocomplete.domain.usecase

import blblblbl.simplelife.autocomplete.domain.repository.AutocompleteRepository
import javax.inject.Inject

class SearchCitiesVariantsUseCase @Inject constructor(
    private val repository:AutocompleteRepository
) {
    suspend fun execute(cityName:String):List<String> =
        repository.searchCities(cityName)
}