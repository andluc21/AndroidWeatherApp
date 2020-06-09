package com.example.ad340weatherapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.ad340weatherapp.*
import com.example.ad340weatherapp.api.CurrentWeather
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast.tempText
import kotlinx.android.synthetic.main.fragment_forecast_details.*
import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.text.SimpleDateFormat
import java.util.Date



private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyy")

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details, container,false)


        // can probably delete these bottom variables vvv ?

        val tempText = layout.findViewById<TextView>(R.id.tempText)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)
        val forecastIcon2 = layout.findViewById<ImageView>(R.id.forecastIcon2)
        val dateText = layout.findViewById<TextView>(R.id.dateText)


        tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = args.description

        dateText.text = DATE_FORMAT.format(Date(args.date * 1000))

        val iconId = args.icon
        forecastIcon2.load("http://openweathermap.org/img/wn/${iconId}@2x.png")

        return layout
    }



}

/*
tempDisplaySettingManager = TempDisplaySettingManager(requireContext())


locationName.text = currentWeather.name
tempText.text = formatTempForDisplay(currentWeather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
date.args = DATE_FORMAT.format(Date(currentWeather.date * 1000))
*/
