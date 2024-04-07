
import kotlinx.serialization.Serializable


@Serializable
data class ResponseData(
    val data: List<DataItem>
)

@Serializable
data class DataItem(
    val humidity: String,
    val id: Int,
    val temperature: String,
    val timestamp_record: String
)