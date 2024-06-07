package com.example.hotelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.hotelapp.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}