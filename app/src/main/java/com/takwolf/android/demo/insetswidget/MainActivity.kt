package com.takwolf.android.demo.insetswidget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.insetswidget.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDemo.setOnClickListener {
            // TODO
        }
    }
}
