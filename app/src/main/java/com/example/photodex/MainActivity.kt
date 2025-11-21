package com.example.photodex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photodex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Nav Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        }
    }