package com.example.kelvindu.masterdetail.presenter.post_detail_fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.kelvindu.masterdetail.db.MainDOImpl
import com.example.kelvindu.masterdetail.model.Position
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.view.post_detail_fragment.PostDetailFragmentView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KelvinDu on 10/18/2017.
 */
class PostDetailPresenterImpl : PostDetailPresenter, LocationListener {
    private lateinit var view: PostDetailFragmentView
    private lateinit var locationManager: LocationManager
    private var currentBestLocation: Location? = null

    override fun subscribe() {
        checkPermission()

        locationManager = view.getActivityFromFragment().getSystemService(Context.LOCATION_SERVICE) as LocationManager

//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.toFloat(), this)

    }

    override fun unSubscribe() {
        locationManager.removeUpdates(this)
    }

    override fun attachView(view: PostDetailFragmentView) {
        this.view = view
    }

//    override fun onConnected(p0: Bundle?) {
//        if (ActivityCompat.checkSelfPermission(view.getActivityFromFragment(),android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (!(ActivityCompat.shouldShowRequestPermissionRationale(view.getActivityFromFragment(),
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)))
//                ActivityCompat.requestPermissions(view.getActivityFromFragment(),
//                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) ,
//                        100)
//        }
//        val location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
//
//
//        if (location != null) {
//            Timber.d("lats:"+ location.latitude +"lon:" + location.longitude)
//            val p = Position(post_id = view.getPostId(),
//                    latitude = location.latitude.toString(),
//                    longitude = location.longitude.toString(),
//                    timestamp = SimpleDateFormat("yyyy/MM/dd - HH:mm:ss", Locale.getDefault())
//                            .format(Calendar.getInstance().time))
//
//            if (MainDOImpl().checkAvailablePosition(view.getPostId())){
//                MainDOImpl().updatePosition(p)
//            } else
//                MainDOImpl().insertPosition(p)
//
//            view.setPositionViews(p)
//
//        }
//        else Timber.e("Why it's null bruh?")
//    }

//    override fun getLocation() {
//        Timber.d("Location currently::" + currentBestLocation!!.longitude)
//        val p = Position(post_id = view.getPostId(),
//                    latitude = currentBestLocation!!.latitude.toString(),
//                    longitude = currentBestLocation!!.longitude.toString(),
//                    timestamp = SimpleDateFormat("yyyy/MM/dd - HH:mm:ss", Locale.getDefault())
//                            .format(Calendar.getInstance().time))
//        if (MainDOImpl().checkAvailablePosition(view.getPostId())){
//                MainDOImpl().updatePosition(p)
//            } else
//                MainDOImpl().insertPosition(p)
//
//        view.setPositionViews(p)
//    }

    override fun getLocation(){
        checkPermission()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.toFloat(), this)
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.toFloat(), this)
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(view.getActivityFromFragment(),android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (!(ActivityCompat.shouldShowRequestPermissionRationale(view.getActivityFromFragment(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)))
                ActivityCompat.requestPermissions(view.getActivityFromFragment(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) ,
                        100)
        }
    }

    override fun loadDetailPost(postId: Long) {
        MainDOImpl().loadById(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p: Post ->
                    view.setPost(p)
                },{t: Throwable? ->
                    Timber.e(t)
                })
    }

    override fun loadPosition(postId: Long) {
        MainDOImpl().loadPosition(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({position: Position ->
                    view.setPositionViews(position)
                },{ t: Throwable? ->
                    Timber.e(t)
                })
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null){
            if (currentBestLocation == null){
                currentBestLocation = location
            } else if (isBetterLocation(location))
                currentBestLocation = location
            Timber.d("Lats:"+location.latitude+" Lon:"+location.longitude+" Time:"+location.time)
            val p = Position(post_id = view.getPostId(),
                    latitude = currentBestLocation!!.latitude.toString(),
                    longitude = currentBestLocation!!.longitude.toString(),
                    timestamp = SimpleDateFormat("yyyy/MM/dd - HH:mm:ss", Locale.getDefault())
                            .format(Calendar.getInstance().time))
            if (MainDOImpl().checkAvailablePosition(view.getPostId())){
                MainDOImpl().updatePosition(p)
            } else
                MainDOImpl().insertPosition(p)

            view.setPositionViews(p)

//            view.setPositionViews(Position(post_id = view.getPostId(),
//                    latitude = location.latitude.toString(),
//                    longitude = location.longitude.toString(),
//                    timestamp = location.time.toString()))
        }
    }

    fun isBetterLocation(location: Location): Boolean {
        if (currentBestLocation == null)
            return true

        val timeDelta = location.time - currentBestLocation!!.time

        if (timeDelta > TWO_MINUTES)
            return true
        else if (timeDelta < TWO_MINUTES)
            return false

        val isNewer = timeDelta > 0
        val accuracyDelta = location.accuracy - currentBestLocation!!.accuracy
        val isSameProvider = isSameProvider(location.provider, currentBestLocation!!.provider)

        if (accuracyDelta < 0) return true
        else if (accuracyDelta > 0 && isNewer) return true
        else return accuracyDelta > 200 && isNewer && isSameProvider

    }

    private fun isSameProvider(provider1: String?, provider2: String?): Boolean {
        return if (provider1 == null) {
            provider2 == null
        } else provider1 == provider2
    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }

    override fun onProviderEnabled(provider: String?) { }

    override fun onProviderDisabled(provider: String?) { }
    companion object {
        const val TWO_MINUTES = 60 * 1000 * 2
    }
}