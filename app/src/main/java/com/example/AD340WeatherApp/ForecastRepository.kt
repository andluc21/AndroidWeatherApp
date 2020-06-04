package com.example.ad340weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

import kotlin.random.Random

class ForecastRepository {

        private val  _currentForecast = MutableLiveData<DailyForecast>()
        val currentForecast: LiveData<DailyForecast> = _currentForecast


    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast


    fun loadWeeklyForecast(zipcode: String) {
        val randomValues = List (7) { Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map {
            DailyForecast(Date(), it, getTempDescription(it))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    fun loadCurrentForecast(zipcode: String) {
        val randomTemp = Random.nextFloat().rem(100) * 100
        val forecast = DailyForecast(Date(), randomTemp, getTempDescription(randomTemp))
        _currentForecast.value = forecast

    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Still quite chilly"
            in 55f.rangeTo(65f) -> "Almost tolerable..."
            in 65f.rangeTo(75f) -> "No overcoat necessary"
            in 75f.rangeTo(85f) -> "Perfection"
            in 85f.rangeTo(95f) -> "Hopefully you have sunscreen"
            in 95f.rangeTo(Float.MAX_VALUE) -> "Yikes!"
            else -> "This doesn't work"
        }
    }
}