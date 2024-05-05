
import kotlinx.serialization.Serializable


@Serializable
data class ResponseData(
    val data: List<DataItem>
)

@Serializable
data class DataItem(
    val humidity: Float,
    val id: String,
    val temperature: Float,
    val timestamp_record: String,
    val difference:Int = 0,
    val now:String = ""
)

@Serializable
data class ResponseLiveData(
    val data: DataItem? = null
)



