package com.jalasoft.routesapp.data.source

import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import com.jalasoft.routesapp.data.model.remote.LinePath
import com.jalasoft.routesapp.data.model.remote.LineInfo
import com.jalasoft.routesapp.data.model.remote.LineRoutePath
import com.jalasoft.routesapp.util.helpers.GoogleMapsHelper

object FakeRoutesData {
    private val points1Array = listOf(
        listOf(-16.520939322501413, -68.12557074070023),
        listOf(-16.521062847351256, -68.12514516472181),
        listOf(-16.52130602845841, -68.12417648825397),
        listOf(-16.521670987319112, -68.12320625310048),
        listOf(-16.52197231180913, -68.12260107624422),
        listOf(-16.522451435494332, -68.12218135682076),
        listOf(-16.523261825566387, -68.12214426533951),
        listOf(-16.523703514803486, -68.1221403609752),
        listOf(-16.523780248849537, -68.12235510114752),
        listOf(-16.524002964559173, -68.12266159393164),
        listOf(-16.524285569842718, -68.12298370418992)
    )

    private val stops1Array = listOf(
        listOf(-16.520939322501413, -68.12557074070023),
        listOf(-16.52130602845841, -68.12417648825397),
        listOf(-16.521670987319112, -68.12320625310048),
        listOf(-16.522451435494332, -68.12218135682076),
        listOf(-16.523780248849537, -68.12235510114752),
        listOf(-16.524285569842718, -68.12298370418992)
    )

    private val points2Array = listOf(
        listOf(-16.5255314,	-68.1254204),
        listOf(-16.5247497,	-68.1251629),
        listOf(-16.5247755,	-68.1246533),
        listOf(-16.5251612,	-68.1243314),
        listOf(-16.5251046,	-68.1238218),
        listOf(-16.5246006,	-68.1232156),
        listOf(-16.5245543,	-68.1218155),
        listOf(-16.5247286,	-68.1216115),
        listOf(-16.5241937,	-68.1204527)
    )

    private val stops2Array = listOf(
        listOf(-16.5255314, -68.1254204),
        listOf(-16.5246006,	-68.1232156),
        listOf(-16.5241937,	-68.1204527)
    )

    private val points3Array = listOf(
        listOf(-16.5206262, -68.1227148),
        listOf(-16.5209862, -68.1229079),
        listOf(-16.5212999, -68.1231064),
        listOf(-16.5216239, -68.1231976),
        listOf(-16.5220662, -68.1233478),
        listOf(-16.5226807, -68.1237467),
        listOf(-16.5230562, -68.1242724),
        listOf(-16.5232053, -68.1245996),
        listOf(-16.5235817, -68.1248782),
        listOf(-16.5237617, -68.124964),
        listOf(-16.5241114, -68.1251303),
        listOf(-16.5244779, -68.1253892)
    )

    private val stops3Array = listOf(
        listOf(-16.5206262, -68.1227148),
        listOf(-16.5216239, -68.1231976),
        listOf(-16.5232053, -68.1245996),
        listOf(-16.5244779, -68.1253892)
    )
    private const val idLine1 = "aqafwegtfe12"
    private const val idLine2 = "gtgglvmsbvk1"
    private const val idLine3 = "vmbrormfkjg9"

    private val start1 = GoogleMapsHelper.coordinatesToLocation(-16.52035351419114, -68.12580890707301)
    private val end1 = GoogleMapsHelper.coordinatesToLocation(-16.524285569842718, -68.12298370418992)
    private val points1 = arrayToMutableListOfLocation(points1Array)
    private val stops1 = arrayToMutableListOfLocation(stops1Array)
    private val line1Info = LineInfo("Line 1", null, "Bus", idLine1)
    private val line1 = LineRoutePath("Line 1", null, "Bus", idLine1, points1, start1, end1, stops1)

    private val start2 = GoogleMapsHelper.coordinatesToLocation(-16.5255314, -68.1254204)
    private val end2 = GoogleMapsHelper.coordinatesToLocation(-16.5241937, -68.1204527)
    private val points2 = arrayToMutableListOfLocation(points2Array)
    private val stops2 = arrayToMutableListOfLocation(stops2Array)
    private val line2Info = LineInfo("Line 2", null, "Mini", idLine2)
    private val line2 = LineRoutePath("Line 2", null, "Mini", idLine2, points2, start2, end2, stops2)

    private val start3 = GoogleMapsHelper.coordinatesToLocation(-16.5206262, -68.1227148)
    private val end3 = GoogleMapsHelper.coordinatesToLocation(-16.5244779, -68.1253892)
    private val points3 = arrayToMutableListOfLocation(points3Array)
    private val stops3 = arrayToMutableListOfLocation(stops3Array)
    private val line3Info = LineInfo("LA", null, "Metro", idLine3)
    private val line3 = LineRoutePath("LA", null, "Metro", idLine3, points3, start3, end3, stops3)

    val lineInfo = listOf(line1Info, line2Info, line3Info)
    val line = listOf(line1, line2, line3)

    val directionsPointLst = listOf(
        LatLng(-16.52469, -68.12539000000001),
        LatLng(-16.524700000000003, -68.12529),
        LatLng(-16.52478, -68.12515),
        LatLng(-16.524820000000002, -68.12522),
        LatLng(-16.52493, -68.12523),
        LatLng(-16.52523, -68.12530000000001),
        LatLng(-16.525540000000003, -68.1254)
    )

    private fun coordinatesToLocation(lat: Double, lon: Double): Location {
        val location = Location(LocationManager.NETWORK_PROVIDER)
        location.longitude = lon
        location.latitude = lat
        return location
    }

    private fun arrayToMutableListOfLocation(list: List<List<Double>>): MutableList<Location> {
        val points: MutableList<Location> = mutableListOf()
        list.forEach {
            val location = GoogleMapsHelper.coordinatesToLocation(it[0], it[1])
            points.add(location)
        }
        return points
    }
}
