package com.jalasoft.routesapp.ui.routes

import android.R.attr.padding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.jalasoft.routesapp.R
import com.jalasoft.routesapp.data.model.remote.AvailableTransport
import com.jalasoft.routesapp.databinding.FragmentPossibleRoutesBinding
import com.jalasoft.routesapp.ui.routes.adapters.PossibleRouteAdapter
import com.jalasoft.routesapp.ui.routes.viewModel.RoutesViewModel
import com.jalasoft.routesapp.util.Extensions.toLatLong
import com.jalasoft.routesapp.util.PreferenceManager
import com.jalasoft.routesapp.util.helpers.GoogleMapsHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PossibleRoutesFragment : Fragment(), OnMapReadyCallback, PossibleRouteAdapter.IPossibleRouteListener {
    private var _binding: FragmentPossibleRoutesBinding? = null
    private val binding get() = _binding!!
    private var mMap: GoogleMap? = null
    private var index: Int = 0

    private val viewModel: RoutesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPossibleRoutes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPossibleRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        viewModel.possibleRoutesList.observe(viewLifecycleOwner) {
            updateRecycler(it.toMutableList())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(),
                R.raw.style_silver
            )
        )
        mMap = googleMap
        setMapOnCurrentCity()
    }

    private fun moveToLocation(location: LatLng, zoom: Float) {
        val cameraTargetLocation = CameraUpdateFactory.newLatLngZoom(location, zoom)
        mMap?.animateCamera(cameraTargetLocation)
    }

    private fun setMapOnCurrentCity() {
        Log.d("Possible Route", PreferenceManager.getCurrentCity(requireContext()))
        val currentLat = PreferenceManager.getCurrentLocationLat(requireContext())
        val currentLng = PreferenceManager.getCurrentLocationLng(requireContext())
        if (currentLat != PreferenceManager.NOT_FOUND && currentLng != PreferenceManager.NOT_FOUND) {
            val location = LatLng(currentLat.toDouble(), currentLng.toDouble())
            moveToLocation(location, 11F)
        } else {
            findNavController().navigate(R.id.action_homeFragment_to_cityPickerFragment)
        }
    }

    private fun setRecycler() {
        binding.bottomSheet.recyclerPossibleRoutes.layoutManager = LinearLayoutManager(requireContext())
        binding.bottomSheet.recyclerPossibleRoutes.adapter = PossibleRouteAdapter(mutableListOf(), this)
    }

    private fun updateRecycler(list: List<AvailableTransport>) {
        (binding.bottomSheet.recyclerPossibleRoutes.adapter as PossibleRouteAdapter).updateList(list.toMutableList())
    }

    override fun onCityTap(possibleRoute: AvailableTransport) {
        mMap?.let {
            it.clear()
            val builder = LatLngBounds.Builder()
            val start = possibleRoute.transports.first().routePoints.first().toLatLong()
            val end = possibleRoute.transports.last().routePoints.last().toLatLong()
            it.addMarker(MarkerOptions().position(start).title(R.string.start_of_route.toString()).icon(GoogleMapsHelper.bitmapFromVector(requireContext(), R.drawable.ic_start_route)))
            it.addMarker(MarkerOptions().position(end).title(R.string.end_of_route.toString()).icon(GoogleMapsHelper.bitmapFromVector(requireContext(), R.drawable.ic_end_route)))
            for (line in possibleRoute.transports) {
                GoogleMapsHelper.drawPolyline(it, line.routePoints, line.color)
                line.routePoints.map {location ->
                    builder.include(location.toLatLong())
                }
            }
            if (possibleRoute.transports.size > 1) {
                context?.let { ctx ->
                    val first = possibleRoute.transports.first().routePoints.last().toLatLong()
                    val second = possibleRoute.transports.last().routePoints.first().toLatLong()
                    it.addMarker(MarkerOptions().position(first).title(R.string.start_of_route.toString()).icon(GoogleMapsHelper.bitmapFromVector(requireContext(), R.drawable.ic_bus_stop)))
                    it.addMarker(MarkerOptions().position(second).title(R.string.end_of_route.toString()).icon(GoogleMapsHelper.bitmapFromVector(requireContext(), R.drawable.ic_bus_stop)))
                    GoogleMapsHelper.connectStops(it, ctx, first, second)
                }
            }
            val bounds = builder.build()
            val padding = 250
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap?.animateCamera(cu)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}