package hu.andersen.weather_station

import DataItem
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.andersen.weather_station.data.KtorApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val network = KtorApiClient()

    private val itemListData = MutableStateFlow(emptyList<DataItem>())

    val itemList = itemListData.asStateFlow()

    private val realItemData = MutableStateFlow(DataItem(0f,"",26f,""))

    val realItem = realItemData.asStateFlow()

    private val toastMessageData = MutableSharedFlow<String>()

    val toastMessage = toastMessageData.asSharedFlow()


    init {

        fetchLiveData()
    }



     fun fetchStatisticsData(date:String){

        viewModelScope.launch {

            val list = network.getStatisticsItems(date)

            Log.d("TAG_KTOR", "fetch Statistics Data: $list ")


            itemListData.emit(list.data)

        }


    }

    fun fetchLiveData(){

        viewModelScope.launch {

            val dataItem = network.getRealData().data

            Log.d("TAG_KTOR", "fetch Live Data: $dataItem ")

//            if (dataItem != null) {
//                toastMessageData.emit("Updated new value")
//                realItemData.emit(DataItem(0f,"", Random(40).nextFloat(),""))
//            }

            realItemData.emit(DataItem(0f,"", Random.nextInt(10,40).toFloat(),""))


        }


    }


}