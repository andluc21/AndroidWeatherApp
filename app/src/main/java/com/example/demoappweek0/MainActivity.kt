package com.example.demoappweek0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // region Setup Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val ourButton1: Button = findViewById(R.id.ourButton1)


        ourButton1.setOnClickListener{
            val zipcode: String = zipcodeEditText.text.toString()




            if( zipcode.length != 5) {

                Toast.makeText(this,R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, zipcode, Toast.LENGTH_SHORT).show()
            }

        }
    }


    override  fun onStart() {
        super.onStart()

    }

    override  fun onResume() {
        super.onResume()

    }


    // endregion Setup Methods

    // region Teardown Methods

    override  fun onPause() {
        super.onPause()

    }


    override  fun onStop() {
        super.onStop()

    }

    override  fun onDestroy() {
        super.onDestroy()

    }



// endregion Teardown Methods











}
