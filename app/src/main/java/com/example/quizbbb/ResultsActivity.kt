package com.example.quizbbb

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizbbb.databinding.ActivityMainBinding
import com.example.quizbbb.databinding.ActivityResultsBinding

class ResultsActivity: AppCompatActivity() {

    private var binding: ActivityResultsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}
