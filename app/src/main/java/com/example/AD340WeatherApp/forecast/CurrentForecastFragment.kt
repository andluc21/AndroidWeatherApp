package com.example.ad340weatherapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340weatherapp.*
import com.example.ad340weatherapp.api.CurrentWeather
import com.example.ad340weatherapp.api.DailyForecast

import com.example.ad340weatherapp.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_current_forecast.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {


    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationName: TextView = view.findViewById(R.id.locationName)

        val tempText: TextView = view.findViewById(R.id.tempText)

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""


        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



        // Create the observer which updates the UI in response to forecast updates

        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            locationName.text = weather.name
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)


        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()

        }

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipzode)

            }
        }


        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)



        return view
    }



    private fun showLocationEntry() {
            val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
            findNavController().navigate(action)
    }



    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args



            return fragment
        }


    }

}
