package uz.islom.ui.fragment.mosque

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.entity.Mosque
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseImageButton
import uz.islom.ui.custom.BaseTextView
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ui.custom.HeaderLayout
import uz.islom.vm.MosquesViewModel
import kotlin.math.round


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

    private val handler by lazy {
        Handler()
    }

    private val bottomSheetDialog by lazy {
        return@lazy BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme).apply {
            setContentView(LinearLayout(context).apply {

            })
        }
    }

    private lateinit var mapView: MapView

    private var map: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(context)
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: Theme): View? {

        return FrameLayout(inflater.context).apply {

            setBackgroundColor(colour(R.color.white))

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                title = string(R.string.mosque)
                theme = appTheme
                setUpBackAction {
                    fragmentManager?.popBackStack()
                }
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(mapView, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })

            addView(LinearLayout(context).apply {
                id = R.id.idRootLayout
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.END
                translationY = dp(56).toFloat()

                setBackgroundColor(appTheme.toolBarColor)

                addView(BaseTextView(context).apply {
                    id = R.id.idNameView
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(appTheme.secondaryColor)
                }, LinearLayout.LayoutParams(0, full, 1f).apply {
                    leftMargin = dp(16)
                })

                addView(BaseTextView(context).apply {
                    id = R.id.idDistanceView
                    gravity = Gravity.CENTER_VERTICAL
                    setPadding(0, 0, 0, 0)
                    setTextSizeSp(12)
                    setTextColor(appTheme.secondaryColor)
                }, LinearLayout.LayoutParams(wrap, full).apply {
                    rightMargin = dp(8)
                    leftMargin = dp(8)
                })

                addView(BaseImageButton(context).apply {
                    id = R.id.idNavigationView
                    setButtonPadding(dp(8))
                    setColorFilter(appTheme.secondaryColor)
                }, LinearLayout.LayoutParams(dp(40), dp(40)))

                addView(BaseImageButton(context).apply {
                    id = R.id.idInfoView
                    gravity = Gravity.CENTER_VERTICAL
                    setButtonPadding(dp(8))
                    setColorFilter(appTheme.secondaryColor)
                }, LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                    rightMargin = dp(8)
                })

            }, FrameLayout.LayoutParams(full, dp(56), Gravity.BOTTOM))

            layoutParams = ViewGroup.LayoutParams(full, full)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, appTheme: Theme) {

        view.findViewById<BaseImageButton>(R.id.idNavigationView).apply {
            setImageResource(R.drawable.ic_navigation)
        }

        view.findViewById<BaseImageButton>(R.id.idInfoView).apply {
            setImageResource(R.drawable.ic_information)
        }

        mapView.apply {
            onCreate(savedInstanceState)
            getMapAsync {
                map = it.apply {

                    uiSettings?.isMyLocationButtonEnabled = true
                    uiSettings?.isMapToolbarEnabled = false

                    setMinZoomPreference(11f)

                    setOnMarkerClickListener {
                        mosquesViewModel.mosques.value?.find { mosque -> mosque.id == it.snippet.toLong() }?.let { mosque ->
                            bindMosque(mosque)
                        }
                        false
                    }

                }

                if (ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(view.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    it.isMyLocationEnabled = true

                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let { l ->
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(l.latitude, l.longitude), 14f))
                            mosquesViewModel.getMosques(LatLng(l.latitude, l.longitude))
                        }
                    }

                } else {
                    checkLocationPermission()
                }
            }
        }

        mosquesViewModel.mosques.observe(this, Observer { mosques ->
            mosques.forEach {
                map?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).snippet(it.id.toString())
                        .icon(view.bitmapDescriptorFromVector(R.drawable.ic_mosque_marker)))
            }
        })
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun bindTheme(appTheme: Theme) {

        view?.findViewById<View>(R.id.idHeaderLayout)?.apply {
            setBackgroundColor(appTheme.toolBarColor)
        }
        view?.findViewById<TextView>(R.id.idTextView)?.apply {
            setTextColor(appTheme.secondaryColor)
        }

        view?.findViewById<BaseImageButton>(R.id.idBackButton)?.apply {
        }

        view?.findViewById<View>(R.id.idRootLayout)?.apply {
            setBackgroundColor(appTheme.toolBarColor)
        }

        view?.findViewById<BaseImageButton>(R.id.idInfoView)?.apply {
            setImageResources(R.drawable.ic_information, appTheme.secondaryColor)
        }

        view?.findViewById<BaseImageButton>(R.id.idNavigationView)?.apply {
            setImageResources(R.drawable.ic_navigation, appTheme.secondaryColor)
        }

        view?.findViewById<TextView>(R.id.idNameView)?.apply {
            setTextColor(appTheme.secondaryColor)
        }
    }

    private fun bindMosque(mosque: Mosque) {

        view?.findViewById<TextView>(R.id.idNameView)?.apply {
            text = mosque.name
        }

        if (mosque.distance != null)
            view?.findViewById<TextView>(R.id.idDistanceView)?.apply {
                string(R.string.mosque_distance_format)?.let {
                    text = String.format(it,round(mosque.distance * 100) / 100)
                }
            }

        view?.findViewById<BaseImageButton>(R.id.idNavigationView)?.apply {
            setOnClickListener {
                bottomSheetDialog.show()
            }
        }

        view?.findViewById<ImageButton>(R.id.idInfoView)?.apply {
            if (mosque.url?.isNotEmpty() == true) {
                setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(mosque.url))) }
            } else {
                setOnClickListener(null)
            }
        }

        showInfo()

        handler.removeCallbacksAndMessages(null)

        handler.postDelayed({ hideInfo() },3000)

    }

    private fun showInfo(){

        view?.findViewById<View>(R.id.mapView)?.let {
            if (it.translationY != -dp(56).toFloat()) {
                ViewCompat.animate(it)
                        .translationY(-dp(56).toFloat())
                        .setDuration(210)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction {}
                        .withEndAction {}
                        .start()

            }
        }

        view?.findViewById<View>(R.id.idRootLayout)?.let {
            if (it.translationY != 0f) {
                ViewCompat.animate(it)
                        .translationY(0f)
                        .setDuration(210)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction {}
                        .withEndAction {}
                        .start()

            }
        }
    }

    private fun hideInfo(){
        view?.findViewById<View>(R.id.mapView)?.let {
            if (it.translationY != 0f) {
                ViewCompat.animate(it)
                        .translationY(0f)
                        .setDuration(210)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction {}
                        .withEndAction {}
                        .start()

            }
        }

        view?.findViewById<View>(R.id.idRootLayout)?.let {

            if (it.translationY != dp(56).toFloat()) {
                ViewCompat.animate(it)
                        .translationY(dp(56).toFloat())
                        .setDuration(210)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction {}
                        .withEndAction {}
                        .start()

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

}