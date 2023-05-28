package blblblbl.simplelife.autocomplete.data.repository

import android.util.Log
import blblblbl.simplelife.autocomplete.data.datasource.ACDatasource
import blblblbl.simplelife.autocomplete.domain.repository.AutocompleteRepository
import javax.inject.Inject

class AutocompleteRepositoryImpl @Inject constructor(
    private val  acDatasource: ACDatasource
):AutocompleteRepository {
    override suspend fun searchCities(cityName: String): List<String> {
        val apiResponse = acDatasource.searchCities(cityName).results.filter { it.city!=null }
        return apiResponse.map { it.city!! }
    }
}