package uz.islom.ui.fragment.qibla

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import uz.islom.R
import uz.islom.fiqh.calculateQibla
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ext.compass.CompassAssistantListener
import uz.islom.ext.compass.CompassListener
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.getMinScreenSize
import uz.islom.ext.string
import uz.islom.fiqh.makkahLat
import uz.islom.fiqh.makkahLng
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.FooterLayout
import uz.islom.ui.custom.HeaderLayout


class QiblaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = QiblaFragment()
    }

    private var mapView: MapView? = null
    private var map: GoogleMap? = null
    private var listener: CompassListener? = null
    private var curDegree = 0f
    private var imageSize = 0

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(context)
        imageSize = (activity?.getMinScreenSize() ?: dp(360)) / 4 * 3
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: Theme): View? {

        return FrameLayout(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                title = string(R.string.qibla)
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(FooterLayout(context).apply {
                id = R.id.idFooterLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))

            addView(LinearLayout(context).apply {

                orientation = LinearLayout.VERTICAL
                gravity = Gravity.BOTTOM

                addView(mapView, LinearLayout.LayoutParams(full, 0, 1f))

                addView(FrameLayout(context).apply {

                    id = R.id.container

                    addView(AppCompatImageView(context).apply {
                        id = R.id.idImageView
                        setImageResource(R.drawable.img_compass)
                    }, FrameLayout.LayoutParams(imageSize, imageSize))

                    addView(AppCompatImageView(context).apply {
                        id = R.id.imageView2
                        setImageResource(R.drawable.img_makka)
                        visibility = View.GONE
                    }, FrameLayout.LayoutParams(imageSize, imageSize))


                }, LinearLayout.LayoutParams(imageSize, imageSize).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    bottomMargin = dp(16)
                    topMargin = dp(16)
                })

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, appTheme: Theme) {
        view.findViewById<HeaderLayout>(R.id.idHeaderLayout).apply {
            onBackListener = object :HeaderLayout.OnBackClickListener{
                override fun onBackClicked() {
                    fragmentManager?.popBackStack()
                }

            }
        }

        mapView?.apply {
            onCreate(savedInstanceState)
            getMapAsync {

                map = it
                map?.uiSettings?.setAllGesturesEnabled(false)

                if (ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    onHasPermissionForLocation(view, it)
                } else {
                    checkLocationPermission()
                }

            }
        }

    }

    private fun onHasPermissionForLocation(view: View, map: GoogleMap) {

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            val qiblaDegree = calculateQibla(location.latitude, location.longitude)

            val pattern = arrayListOf(Dash(dp(16).toFloat()), Gap(dp(4).toFloat()))

            map.addPolyline(PolylineOptions().width(dp(4).toFloat()).clickable(true).addAll(listOf(LatLng(makkahLat, makkahLng), LatLng(location.latitude, location.longitude))).pattern(pattern).geodesic(true))
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds(
                    LatLng(makkahLat, makkahLng),
                    LatLng(location.latitude, location.longitude)),
                    (activity?.getMinScreenSize() ?: dp(360)) / 100 * 8)
            )

            view.findViewById<ImageView>(R.id.imageView2)?.apply {
                rotation = qiblaDegree.toFloat()
                visibility = View.VISIBLE
            }

            listener = CompassListener(view.context, location.latitude.toFloat(), location.longitude.toFloat(), 0f).apply {
                addListener(object : CompassAssistantListener {
                    override fun onNewDegreesToNorth(degrees: Float) {}

                    override fun onNewSmoothedDegreesToNorth(degrees: Float) {
                        val ra = RotateAnimation(curDegree, degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                        ra.duration = 210
                        ra.fillAfter = true

                       // view.findViewById<View>(R.id.container)?.startAnimation(ra)

                        curDegree = degrees
                    }

                    override fun onCompassStopped() {}

                    override fun onCompassStarted() {}

                })
            }

        }

    }

    override fun onResume() {
        mapView?.onResume()
        listener?.start()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        listener?.stop()
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

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            }
        }
    }


}