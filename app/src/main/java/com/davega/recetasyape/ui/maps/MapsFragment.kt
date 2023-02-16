package com.davega.recetasyape.ui.maps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.davega.recetasyape.R
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding, BaseViewModel>(), OnMapReadyCallback {
    override val viewModel: MapsViewModel  by viewModels()

    override fun getViewBinding(): FragmentMapsBinding = FragmentMapsBinding.inflate(layoutInflater)

    private val args: MapsFragmentArgs by navArgs()

    private lateinit var map: GoogleMap
    private var latOriginFood:Double = 23232323.0
    private var lngOriginFood:Double = -122132.2
    private var nameFood: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
        setupMaps()
    }

    private fun setupArgs(){
        latOriginFood = args.lat.toDouble()
        lngOriginFood = args.lng.toDouble()
        nameFood = args.name
    }

    private fun setupMaps(){
        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapsFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker(){
        val coordinates = LatLng(latOriginFood, lngOriginFood)
        val marker = MarkerOptions().position(coordinates).title(nameFood)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            3000,
            null
        )

    }


}