package com.yuki.map

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val japan = LatLng(35.0, 139.0)
        mMap.mapType = MAP_TYPE_NORMAL
        mMap.apply {
            setMaxZoomPreference(6f)
            setMinZoomPreference(3f)
            setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(22.0,123.0), LatLng(46.0,154.0)))
        }

        mMap.setOnMapClickListener{
                val location:LatLng = it
                val coder = Geocoder(applicationContext, Locale.JAPANESE)
                val address:List<Address> = coder.getFromLocation(location.latitude,location.longitude,1)
                //日本の時だけ県名取得
                if (address[0].countryCode=="JP") {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(address[0].adminArea.toString())
                    )
                }
            }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(japan,5f))
    }
}