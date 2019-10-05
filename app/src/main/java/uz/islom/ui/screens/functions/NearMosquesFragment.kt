package uz.islom.ui.screens.functions

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uz.islom.R
import uz.islom.android.colour
import uz.islom.android.string
import uz.islom.ui.FragmentNavigator
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.ui.util.dp
import uz.islom.ui.util.full
import uz.islom.ui.util.setTextSizeSp
import uz.islom.vm.MosquesViewModel


class NearMosquesFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = NearMosquesFragment()
    }

    private val mosquesViewModel by lazy {
        ViewModelProviders.of(this).get(MosquesViewModel::class.java)
    }

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private var mapView: MapView? = null
    private var map: GoogleMap? = null

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(inflater.context).apply {

            setBackgroundColor(colour(R.color.white))

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.backButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER_VERTICAL
                    text = string(R.string.mosque)
                    setTextSizeSp(18)
                }, FrameLayout.LayoutParams(full, full).apply {
                    leftMargin = dp(72)
                    rightMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, dp(56)))

            mapView = MapView(context)

            addView(mapView, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })

            layoutParams = ViewGroup.LayoutParams(full, full)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<BaseImageButton>(R.id.backButton).apply {
            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
            setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        mapView?.apply {
            onCreate(savedInstanceState)
            getMapAsync {
                map = it.apply {
                    uiSettings?.isMyLocationButtonEnabled = true
                    setMinZoomPreference(11f)
                    setOnMarkerClickListener { marker ->
                        mosquesViewModel.mosques.value?.find { mosque -> mosque.id == marker.snippet.toLong() }?.let { mosque ->
                            (activity as? FragmentNavigator)?.navigateToMosqueInfo(mosque)
                        }
                        true
                    }
                }

                if (ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    it.isMyLocationEnabled = true

                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let { l ->
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(l.latitude, l.longitude), 14f))
                            mosquesViewModel.getMosques(l, 10000)
                        }
                    }

                } else {
                    checkLocationPermission()
                }

                mosquesViewModel.mosques.observe(this@NearMosquesFragment, Observer { mosques ->
                    mosques.forEach { mosque ->
                        it.addMarker(MarkerOptions().title(mosque.name).position(LatLng(mosque.lat, mosque.lng)).snippet(mosque.id.toString()))
                    }
                })
            }
        }
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            }
        }
    }


    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

}