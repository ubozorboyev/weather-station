package hu.andersen.weather_station.data

import DataItem
import ResponseData
import ResponseLiveData
import io.ktor.client.HttpClient
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class KtorApiClient {



    private val httpClient = HttpClient {

        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

        install(DefaultRequest){
            header("Authorization","temperature-himidity")
           // url("https://temperature-humidity.up.railway.app/api/weather-service/")
        }

        install(Logging) {
            level = LogLevel.ALL
        }



    }

    suspend fun getStatisticsItems(date:String): ResponseData {


        return httpClient.get<ResponseData>{

            url("https://temperature-humidity.up.railway.app/api/weather-service/getStatistics")

            parameter("from_date", date)
            parameter("to_date",date)
        }

    }


    suspend fun getRealData(): ResponseLiveData {

        return httpClient.get<ResponseLiveData>( "https://temperature-humidity.up.railway.app/api/weather-service/live")
    }
}

