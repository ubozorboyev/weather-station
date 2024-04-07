package hu.andersen.weather_station

import DataItem
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import hu.andersen.weather_station.ui.theme.WeatherstationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        setContent {
            WeatherstationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val itemsState = viewModel.itemList.collectAsState()


                    Greeting("Android", context= this,itemsState.value )
                }
            }
        }

        lifecycleScope.launch {

            viewModel.itemList.collectLatest {

                Log.d("TAG_KTOR", "onCreate:$it ")
            }


        }

    }
}

@Composable
fun Greeting(name: String,  context:Context, list: List<DataItem>) {




    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .fillMaxHeight()){

        Text(
            text = "Weather Station",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally) ,
            fontSize = 32.sp

        )


        LazyColumn {
            items(list) { item ->
                WeatherItem(item)
            }
        }

        Button(onClick = {

            Toast.makeText(context, "Updating values", Toast.LENGTH_SHORT).show()
        },
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Bottom)
                .padding(10.dp) ) {
            Text(text = "Update Value ", fontSize = 18.sp)
        }

    }
}


@Composable
fun WeatherItem(item: DataItem){

    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(10.dp))


    Text(
        text = "Temperature",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally) ,
        fontSize = 24.sp

    )

    Text(
        text = item.temperature,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.White)
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally) ,
        fontSize = 32.sp

    )


    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(40.dp))


    Text(
        text = "Humidity",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally) ,
        fontSize = 24.sp

    )

    Text(
        text = item.humidity,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.White)
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally) ,
        fontSize = 32.sp,

        )

    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherstationTheme {
        //Greeting("Android")
    }
}