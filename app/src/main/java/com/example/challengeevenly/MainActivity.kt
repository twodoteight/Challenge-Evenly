package com.example.challengeevenly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeevenly.databinding.ActivityMainBinding
import com.example.challengeevenly.databinding.FragmentPlacesBinding
import com.example.challengeevenly.model.Place
import com.example.challengeevenly.places.PlacesListAdapter
import com.example.challengeevenly.places.PlacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
