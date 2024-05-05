package hu.andersen.weather_station

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import io.github.farshidroohi.ChartEntity
import io.github.farshidroohi.LineChart
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


//https://github.com/FarshidRoohi/LineGraph?tab=readme-ov-file
class LineGraphActivity : ComponentActivity(){


    private lateinit var lineChart: LineChart
    private lateinit var textView: TextView

    private lateinit var button: Button

    private val viewModel by viewModels<MainViewModel>()

   private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.line_graph_activity)

        viewModel.fetchStatisticsData(dateFormat.format(Date()))


         lineChart = findViewById<LineChart>(R.id.lineChart)
         textView = findViewById<TextView>(R.id.lineChartValue)
         button = findViewById<Button>(R.id.filter_button)



        //lineChart.setLegend(dateLrr)

        lineChart.setOnDistanceChangeListener {

            textView.text = it.toString()
        }


        setUpObserves()


        val dialog = DatePickerDialog(this)

        dialog.datePicker.maxDate = System.currentTimeMillis()


        dialog.setOnDateSetListener { view, year, month, dayOfMonth ->

            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            viewModel.fetchStatisticsData(dateFormat.format(calendar.time))

        }


        button.setOnClickListener {

            dialog.show()

        }




    }


    private fun setUpObserves(){


        lifecycleScope.launch {


            viewModel.itemList.collectLatest {

                if (it.isEmpty()) return@collectLatest




                val humidityChartEntity = it.map {

                    it.humidity

                }.takeLast(9)

                val temperatureChartEntity = it.map {

                    it.temperature
                }.takeLast(9)



                val firstChartEntity = ChartEntity(Color.WHITE, humidityChartEntity.toFloatArray())
                val secondChartEntity = ChartEntity(Color.YELLOW, temperatureChartEntity.toFloatArray())

                Log.d("TAG_CHART", "first chart: ${humidityChartEntity} ")
                Log.d("TAG_CHART", "second chart: ${temperatureChartEntity} ")

                    val list = ArrayList<ChartEntity>().apply {
                        add(firstChartEntity)
                        add(secondChartEntity)
                    }

                Log.d("TAG_CHART", "list: ${list} ")


                    lineChart.setList(list)


            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerView() {
        val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })


        val selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            DatePicker(
                state = datePickerState
            )

            Spacer(
                modifier = Modifier.height(
                    32.dp
                )
            )


//            Text(
//                text = selectedDate.toString(),
//                fontSize = 20.sp,
//                color = Color.RED,
//                modifier = Modifier
//            )
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }




}

