package com.example.challengeevenly.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeevenly.databinding.FragmentPlacesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlacesFragment : Fragment() {

    lateinit var binding: FragmentPlacesBinding
    lateinit var placesListAdapter: PlacesListAdapter
    lateinit var navController: NavController

    private val viewModel: PlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchNearbyPlaces()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        placesListAdapter = PlacesListAdapter(this) {
            viewModel.selectPlace(it)
            // Passing the selected place to the next fragment to show.
            // Redundant, but added to show that this is also possible
            val action = PlacesFragmentDirections.actionShowMap(it)
            navController.navigate(action)
        }

        // UI Elements
        setUpUIElements()
        // Observers
        setUpObservers()
    }

    private fun setUpUIElements() {
        binding.rvPlacesList.apply {
            adapter = placesListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.placesListState.collect {
                binding.pbPlacesList.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                placesListAdapter.updateData(it.data)
                println("Places data changed")
            }
        }
    }
}