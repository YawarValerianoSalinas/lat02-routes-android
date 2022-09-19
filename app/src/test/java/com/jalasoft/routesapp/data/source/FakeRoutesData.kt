package com.jalasoft.routesapp.data.source

import android.location.Location
import com.jalasoft.routesapp.data.model.local.LineCategoriesEntity
import com.jalasoft.routesapp.data.model.local.LineEntity
import com.jalasoft.routesapp.data.model.remote.LinePath
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

    private val start1 = GoogleMapsHelper.coordinatesToLocation(-16.52035351419114, -68.12580890707301)
    private val end1 = GoogleMapsHelper.coordinatesToLocation(-16.524285569842718, -68.12298370418992)
    private val points1 = arrayToMutableListOfLocation(points1Array)
    private val stops1 = arrayToMutableListOfLocation(stops1Array)
    private val line1 = LinePath("Line 1", "Bus", points1, start1, end1, stops1)

    private val start2 = GoogleMapsHelper.coordinatesToLocation(-16.5255314, -68.1254204)
    private val end2 = GoogleMapsHelper.coordinatesToLocation(-16.5241937, -68.1204527)
    private val points2 = arrayToMutableListOfLocation(points2Array)
    private val stops2 = arrayToMutableListOfLocation(stops2Array)
    private val line2 = LinePath("Line 2", "Mini", points2, start2, end2, stops2)

    private val start3 = GoogleMapsHelper.coordinatesToLocation(-16.5206262, -68.1227148)
    private val end3 = GoogleMapsHelper.coordinatesToLocation(-16.5244779, -68.1253892)
    private val points3 = arrayToMutableListOfLocation(points3Array)
    private val stops3 = arrayToMutableListOfLocation(stops3Array)
    private val line3 = LinePath("LA", "Metro", points3, start3, end3, stops3)

    val lines = listOf(line1, line2, line3)

    private fun arrayToMutableListOfLocation(list: List<List<Double>>): MutableList<Location> {
        val points: MutableList<Location> = mutableListOf()
        list.forEach {
            val location = GoogleMapsHelper.coordinatesToLocation(it[0], it[1])
            points.add(location)
        }
        return points
    }
    private val lineEntity = LineEntity("1", "line test", "scz", "bus", true)
    private val lineCategory = LineCategoriesEntity("123", "bus", "bus")
    private val lineRoutePath = LineRoutePath("123", "ruta de ida", "1234", "line test", start1, end1, points1, stops1)
    val linesEntity = listOf(lineEntity)
    val lineCategoriesEntity = listOf(lineCategory)
    val lineRoutesPath = listOf(lineRoutePath)
}
