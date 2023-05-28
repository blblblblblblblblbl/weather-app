package blblblbl.simplelife.autocomplete.data.network

import blblblbl.simplelife.autocomplete.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ACApi {
    @GET("autocomplete")
    suspend fun searchCities(
        @Query("text") text: String,
        @Query("limit") limit: Int,
        @Query("type") type: String
    ): Response

}