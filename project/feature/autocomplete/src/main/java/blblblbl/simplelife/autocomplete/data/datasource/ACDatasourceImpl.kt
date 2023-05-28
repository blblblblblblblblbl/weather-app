package blblblbl.simplelife.autocomplete.data.datasource

import blblblbl.simplelife.autocomplete.data.model.Response
import blblblbl.simplelife.autocomplete.data.network.ACApi
import javax.inject.Inject

class ACDatasourceImpl @Inject constructor(
    private val acApi: ACApi
):ACDatasource {
    override suspend fun searchCities(cityName: String): Response =
        acApi.searchCities(cityName,10,"city")
}