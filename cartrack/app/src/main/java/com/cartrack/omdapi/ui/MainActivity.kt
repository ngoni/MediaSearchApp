package com.cartrack.omdapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrack.omdapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater)
    }

}