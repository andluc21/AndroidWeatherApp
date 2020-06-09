package com.example.ad340weatherapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340weatherapp.*
import com.example.ad340weatherapp.api.DailyForecast
import com.example.ad340weatherapp.api.WeeklyForecast

import com.example.ad340weatherapp.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)



        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())


        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
        }

        forecastList.adapter = dailyForecastAdapter

        // Create the observer which updates the UI in response to forecast updates


        val weeklyForecastObserver = Observer<WeeklyForecast> { weeklyForecast ->
            // update our list adapter
            dailyForecastAdapter.submitList(weeklyForecast.daily)
        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)


        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()


        }


        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> forecastRepository.loadWeeklyForecast(savedLocation.zipzode)
            }

        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)


        return view
    }



   private fun showLocationEntry() {
       val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
       findNavController().navigate(action)


   }

    private fun showForecastDetails(forecast: DailyForecast) {
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val icon = forecast.weather.get(0).icon
        val date = forecast.date
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp, description,icon ,date)
        findNavController().navigate(action)


    }




}
