package com.jalasoft.routesapp.data.model.remote

import android.location.Location
import android.location.LocationManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import java.io.Serializable
import java.util.*

data class TourPoint(
    val idCity: DocumentReference? = null,
    val name: String? = "",
    val address: String? = "",
    val destination: GeoPoint? = null,
    val urlImage: String? = "",
    val tourPointsCategoryRef: DocumentReference? = null
) : Serializable {
    companion object {
        fun geoPointToLocation(data: GeoPoint): Location {
            val newLocation = Location(LocationManager.NETWORK_PROVIDER)
            newLocation.latitude = data.latitude
            newLocation.longitude = data.longitude
            return newLocation
        }
    }

    suspend fun tourPointToTourPointPath(): TourPointPath {
        val destination = destination?.let { TourPoint.geoPointToLocation(it) }
        var city: DocumentSnapshot?
        var cityId = ""
        idCity?.let { docRef ->
            city = docRef.get().await()
            city?.let {
                cityId = it.toObject(City::class.java)?.id ?: ""
            }
        }
        var category: DocumentSnapshot?
        var categoryName = ""
        tourPointsCategoryRef?.let { docRef ->
            category = docRef.get().await()
            category?.let {
                val currLang = Locale.getDefault().isO3Language
                categoryName =
                    if (currLang == "spa") it.toObject(TourPointsCategory::class.java)?.descriptionEsp ?: ""
                    else it.toObject(TourPointsCategory::class.java)?.descriptionEng ?: ""
            }
        }
        return TourPointPath(cityId, name, address, destination, urlImage, categoryName)
    }
}

data class TourPointPath(
    val idCity: String? = "",
    val name: String? = "",
    val address: String? = "",
    val destination: Location? = null,
    val urlImage: String? = "",
    val category: String? = ""
) : Serializable
