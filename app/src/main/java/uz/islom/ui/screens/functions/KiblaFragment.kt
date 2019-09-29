package uz.islom.ui.screens.functions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import uz.islom.R
import uz.islom.fiqh.calculateQibla
import uz.islom.sensor.CompassListener
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.util.*


class KiblaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = KiblaFragment()
    }

    var mapView: MapView? = null
    var map: GoogleMap? = null
    var listener: CompassListener? = null

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val imageSize = (activity?.getMinScreenSize() ?: dp(360)) / 4 * 3

        return FrameLayout(inflater.context).apply {

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.backButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER_VERTICAL
                    text = string(R.string.kibla)
                    setTextSizeSp(18)
                }, FrameLayout.LayoutParams(full, full).apply {
                    leftMargin = dp(72)
                    rightMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, dp(56)))

            mapView = MapView(context)

            addView(mapView, FrameLayout.LayoutParams(full, dp(196)).apply {
                topMargin = dp(56)
            })

            addView(FrameLayout(context).apply {

                id = R.id.container

                addView(AppCompatImageView(context).apply {
                    id = R.id.imageView
                }, FrameLayout.LayoutParams(imageSize, imageSize, Gravity.CENTER_HORIZONTAL))

                addView(AppCompatImageView(context).apply {
                    id = R.id.imageView2
                }, FrameLayout.LayoutParams(imageSize, imageSize, Gravity.CENTER_HORIZONTAL))


            }, FrameLayout.LayoutParams(imageSize, imageSize, Gravity.CENTER_HORIZONTAL).apply {
                topMargin = dp(308)
            })


            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val padding = (activity?.getMinScreenSize() ?: dp(360)) / 100 * 8


        view.findViewById<BaseImageButton>(R.id.backButton).apply {
            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
            setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        val makkah = LatLng(21.42250833, 39.82616111)
        val tashkent = LatLng(41.0, 69.0)
        var curDegree = 0f


        val qiblaDegree = calculateQibla(tashkent.latitude, tashkent.longitude)

        mapView?.apply {
            onCreate(savedInstanceState)
            getMapAsync {
                map = it

                map?.uiSettings?.isMyLocationButtonEnabled = true
                map?.addPolyline(PolylineOptions().clickable(true).addAll(listOf(makkah, tashkent)))
                map?.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds(makkah, tashkent), padding))

            }
        }

        view.findViewById<ImageView>(R.id.imageView).apply {
            setImageResource(R.drawable.img_compass)
        }

        view.findViewById<ImageView>(R.id.imageView2).apply {
            setImageResource(R.drawable.img_makka)
            rotation = qiblaDegree.toFloat()
        }


        listener = CompassListener(view.context, tashkent.latitude.toFloat(), tashkent.longitude.toFloat(), 0f)
        listener?.addListener(object : CompassListener.CompassAssistantListener {
            override fun onNewDegreesToNorth(degrees: Float) {}

            override fun onNewSmoothedDegreesToNorth(degrees: Float) {
                val ra = RotateAnimation(curDegree, degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                ra.duration = 210
                ra.fillAfter = true

                view.findViewById<View>(R.id.container)?.startAnimation(ra)

                curDegree = degrees
            }

            override fun onCompassStopped() {}

            override fun onCompassStarted() {}

        })


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


}