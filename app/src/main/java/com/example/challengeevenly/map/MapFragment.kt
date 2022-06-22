package com.example.challengeevenly.map

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.challengeevenly.R
import com.example.challengeevenly.databinding.FragmentMapBinding
import com.example.challengeevenly.model.*
import com.example.challengeevenly.places.PlacesListAdapter
import com.example.challengeevenly.places.PlacesViewModel
import com.example.challengeevenly.util.loadStandart
import com.example.challengeevenly.util.toPx
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapBinding
    lateinit var photosAdapter: PhotosAdapter

    private val args: MapFragmentArgs by navArgs()
    private val viewModel: PlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchPlacePhotos(viewModel.selectedPlace.fsqID)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosAdapter = PhotosAdapter()

        //Set up map and UI
        setUpUIElements(viewModel.selectedPlace)
        setUpObservers()
        setUpMap()
    }

    private fun setUpUIElements(place: Place) {
        binding.apply {
            vpPlacePhotos.offscreenPageLimit = 3
            vpPlacePhotos.setPageTransformer(getTransfromation())
            vpPlacePhotos.adapter = photosAdapter
            tvMapPlaceName.text = place.name
            tvMapPlaceCategories.text = place.categories.joinToString(" | ") {it.name}
            tvPlaceAddressFirst.text = place.location.address ?: getString(R.string.no_address_text)
            tvPlaceAddressSecond.text = place.getPostcodeLocality()
            bPlaceLinkShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, place.getFoursquareURL())
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share via..."))
            }
        }
    }

    private fun getTransfromation(): CompositePageTransformer {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(30))
        transform.addTransformer { page, position ->
            page.scaleY = 0.85f + (1 - kotlin.math.abs(position)) * 0.15f
        }
        return transform
    }


    private fun setUpObservers() {
        viewModel.currentPhotosState.observe(viewLifecycleOwner) {
            binding.pbPlacePhotos.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            photosAdapter.updateData(it.data)
        }
    }

    private fun setUpMap() {
        if(!::map.isInitialized) {
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.mfMap) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setPadding(0,0,0, 16.toPx())
        map.setBuildingsEnabled(false)

        val place = viewModel.selectedPlace
        val placeCoordinates = LatLng(place.geocodes.main.latitude, place.geocodes.main.longitude)
        map.addMarker(
            MarkerOptions()
                .position(placeCoordinates)
                .anchor(0.5f, 0.5f)
                .title(place.name)
                .snippet(place.categories[0].name)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeCoordinates, 18f))
    }

    private fun copyToClipboard(text: CharSequence) {
        val clipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clipData)
    }
    private fun pasteFromClipboard(): CharSequence? {
        val clipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        return clipboard.primaryClip?.getItemAt(0)?.text
    }
}