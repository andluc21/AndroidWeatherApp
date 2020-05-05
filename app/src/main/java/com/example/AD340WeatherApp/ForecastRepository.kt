package com.example.AD340WeatherApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {


    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast
    fun loadForecast(zipcode: String) {
        val randomValues = List (10) { Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Still quite chilly"
            in 55f.rangeTo(65f) -> "Almost there..."
            in 65f.rangeTo(75f) -> "Finally some enjoyable weather"
            in 75f.rangeTo(85f) -> "Perfection"
            in 85f.rangeTo(95f) -> "Hopefully you have sunscreen"
            in 95f.rangeTo(Float.MAX_VALUE) -> "Yikes!"
            else -> "This doesn't work"
        }
    }
}