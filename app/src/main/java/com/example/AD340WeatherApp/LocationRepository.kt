package com.example.ad340weatherapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


sealed class Location {
    data class Zipcode(val zipzode: String) : Location()


}


private const val KEY_ZIPCODE = "key_zipcode"






class LocationRepository(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation


    init {
        preferences.registerOnSharedPreferenceChangeListener{ sharedPreferences, key ->
            if (key != KEY_ZIPCODE)  return@registerOnSharedPreferenceChangeListener
            broadcastSavedZipCode()
        }

        broadcastSavedZipCode()

    }

    fun  saveLocation(location: Location) {
        when (location) {
                    is Location.Zipcode -> preferences.edit().putString(KEY_ZIPCODE, location.zipzode).apply()


        }

    }

    private fun broadcastSavedZipCode() {
        val zipzode = preferences.getString(KEY_ZIPCODE, "")
        if(zipzode != null && zipzode.isNotBlank()) {
            _savedLocation.value = Location.Zipcode(zipzode)
        }
    }

}