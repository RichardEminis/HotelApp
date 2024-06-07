package com.example.hotelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.hotelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<HotelsFragment>(R.id.mainContainer)
            addToBackStack(null)
        }
    }
}