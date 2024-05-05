package hu.andersen.weather_station

import DataItem
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.lifecycleScope
import hu.andersen.weather_station.ui.theme.WeatherstationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.SimpleDateFormat

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        setContent {
            WeatherstationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()

                ) {

                    Box(modifier = Modifier.paint(
                        painterResource(id = R.drawable.main_screen_bg),
                        contentScale = ContentScale.FillBounds
                    )){

                        MainScreen(viewModel)

                    }


                }
            }
        }

        hideSystemUI()




        lifecycleScope.launch {

            viewModel.toastMessage.collect{
                Toast.makeText(this@MainActivity,it,Toast.LENGTH_SHORT).show()
            }


        }


    }
    private fun hideSystemUI() {

        //Hides the ugly action bar at the top
        actionBar?.hide()

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Hide the status bars

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        } else {
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars())
//                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        }
    }




    override fun onResume() {
        super.onResume()


    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {


    val liveItem = viewModel.realItem.collectAsState()

    val context = LocalContext.current


    Row(modifier = Modifier
        .padding(top = 50.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth()
        .fillMaxHeight()){


        AndroidView(::ThermometerView, modifier = Modifier
            .height(700.dp)
            .width(160.dp)) { customView ->

            customView.initConfig()

            customView.setValueAndStartAnim(liveItem.value.temperature)


        }

        Box(Modifier.fillMaxHeight()) {

            val isCelsius = remember {

                mutableStateOf(true)
            }


            Row (
                Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                CircularDeterminateIndicator(viewModel)

                Spacer(modifier = Modifier.weight(20f))

                Image(painter = painterResource(id = R.drawable.ic_assessment), contentDescription ="sfd",

                    Modifier.clickable {

                        val chartIntent = Intent(context, LineGraphActivity::class.java)

                        context.startActivity(chartIntent)
                    } )

            }


            Text(text = if (isCelsius.value) "${liveItem.value.temperature} ℃"
            else ThermometerView.getFahrenheitValues(liveItem.value.temperature),
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle.Default,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {

                        isCelsius.value = isCelsius.value.not()


                    })


        }








//        Button(
//            onClick = {
//
//
//
//            },
//            Modifier
//                .fillMaxWidth()
//                .padding(vertical = 20.dp, horizontal = 16.dp)
//                .wrapContentHeight(),
//
//
//            ) {
//            Text(text = "Statistics ", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
//        }

    }
}


@Composable
fun CircularDeterminateIndicator(viewModel: MainViewModel) {

    var currentProgress by remember { mutableIntStateOf(60) }


   LaunchedEffect(Unit){


       while (true){

           for (i in 60 downTo 0){

               currentProgress = i

               delay(500)

           }

           delay(1000)

           currentProgress = 60

           viewModel.fetchLiveData()


       }



   }

    Column(

        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.wrapContentWidth()
    ) {

        
        BoxWithConstraints(
        ) {
            
            CircularProgressIndicator(
                progress = { currentProgress/60f },
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center),
                strokeWidth = 4.dp,
                color = Color.White,
                trackColor = MaterialTheme.colorScheme.inversePrimary

            )
            
            Text(
                text = "$currentProgress",
                modifier =Modifier.align(Alignment.Center),
                fontSize = 14.sp,
                color = Color.White
            )
            
            
        }

    }
}


@Composable
fun WeatherItem(item: DataItem){

    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(10.dp))

    Log.d("LOG_ITEM","temperature : ${item.temperature}")
    Log.d("LOG_ITEM","humidity : ${item.humidity}")


    OutlinedCard(
        elevation = CardDefaults.outlinedCardElevation(
            10.dp,

        )
    ) {

        Column {

            Text(
                text = "${item.temperature}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally) ,
                fontSize = 32.sp)

            Text(
                text = "Temperature",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
//            .border(1.dp, Color.White)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(8.dp),
                fontSize = 20.sp,
                color = Color.Magenta,


            )

        }


    }






    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(40.dp))


//    Text(
//        text = "Humidity: : ${item.humidity}",
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .padding(8.dp)
//            .wrapContentWidth(Alignment.CenterHorizontally) ,
//        fontSize = 24.sp
//
//    )

    Log.d("LOG_ITEM","temperature : ${item.temperature}")
    Log.d("LOG_ITEM","humidity : ${item.humidity}")

    Text(
        text = "Humidity: ${item.humidity}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
//            .border(1.dp, Color.White)
            .padding(8.dp),
        fontSize = 32.sp,
        color = Color.White
        )

    Spacer(modifier = Modifier
        .width(IntrinsicSize.Max)
        .height(10.dp))
}