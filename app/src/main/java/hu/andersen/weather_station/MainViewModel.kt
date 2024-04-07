package hu.andersen.weather_station

import DataItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.andersen.weather_station.data.KtorApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val network = KtorApiClient()

    private val itemListData = MutableStateFlow(emptyList<DataItem>())

    val itemList = itemListData.asStateFlow()


    init {
        fetchData()
    }



    private fun fetchData(){

        viewModelScope.launch {

            val list = network.getDataItems()

            Log.d("TAG_KTOR", "fetchData: $list ")

            itemListData.emit(list.data)
        }


    }


}