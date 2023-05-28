package blblblbl.simplelife.autocomplete.data.datasource

import blblblbl.simplelife.autocomplete.data.model.Response

interface ACDatasource {
    suspend fun searchCities(cityName: String): Response
}