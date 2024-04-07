package hu.andersen.weather_station.data

import DataItem
import ResponseData
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom

class KtorApiClient {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getDataItems(): ResponseData {
        val url = URLBuilder().apply {
            takeFrom("http://192.168.242.191:3001/api/weather-service/getStatistics") // Replace with your API endpoint
        }

        val response = httpClient.get<ResponseData>("http://192.168.242.191:3001/api/weather-service/getStatistics"){
            header("Authorization","SomeStaff")

        }

        return response
    }
}