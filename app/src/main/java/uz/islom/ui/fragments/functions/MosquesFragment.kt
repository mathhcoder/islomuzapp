package uz.islom.ui.fragments.functions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import uz.islom.R
import uz.islom.ui.base.BaseFragment
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.util.*


class MosquesFragment : BaseFragment() {

    companion object {
        fun newInstance() = MosquesFragment()
    }

    var mapView: MapView? = null
    var map: GoogleMap? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

            addView(MapView(context).apply {
                id = R.id.container
            }, FrameLayout.LayoutParams(full, full).apply {
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

        view.findViewById<MapView>(R.id.container).apply {
            onCreate(savedInstanceState)
            getMapAsync {
                map = it
                map?.uiSettings?.isMyLocationButtonEnabled = true
                map?.isMyLocationEnabled = true
                map?.setMinZoomPreference(11f)
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