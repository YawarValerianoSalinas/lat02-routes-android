package com.jalasoft.routesapp.data.local.room.managers

import com.jalasoft.routesapp.data.local.room.db.RoutesDB
import com.jalasoft.routesapp.data.local.room.interfaces.LocalDataBaseRepository
import com.jalasoft.routesapp.data.model.local.*
import com.jalasoft.routesapp.data.model.remote.LineRoutePath

class LocalDataBaseManager(private val localRoutesDB: RoutesDB) : LocalDataBaseRepository {
    override fun addLocalLine(line: LineEntity) {
        localRoutesDB.lineDao().addLine(line)
    }

    override fun addLocalLineCategory(lineCategory: LineCategoriesEntity) {
        localRoutesDB.lineCategoryDao().addLineCategory(lineCategory)
    }

    override fun addLocalLineRoute(lineRoutePath: List<LineRoutePath>) {
        for (item in lineRoutePath) {
            val start = Location(item.start?.latitude ?: 0.0, item.start?.longitude ?: 0.0)
            val end = Location(item.end?.latitude ?: 0.0, item.end?.longitude ?: 0.0)
            val lineLocal = LineRouteEntity(item.id, item.idLine, item.name, start, end)
            localRoutesDB.lineRouteDao().addLineRoute(lineLocal)
            addLocalRoutePoints(item.id, item.routePoints)
            addLocalStops(item.id, item.stops)
        }
    }

    override fun addLocalRoutePoints(idLineRoute: String, routePoints: List<android.location.Location>) {
        for (item in routePoints) {
            val location = Location(item.latitude, item.longitude)
            val routePointsLocal = RoutePointsHolder(0, idLineRoute, location)
            localRoutesDB.lineRouteDao().addRoutePoints(routePointsLocal)
        }
    }

    override fun addLocalStops(idLineRoute: String, stops: List<android.location.Location>) {
        for (item in stops) {
            val location = Location(item.latitude, item.longitude)
            val stopsPoints = StopsHolder(0, idLineRoute, location)
            localRoutesDB.lineRouteDao().addStops(stopsPoints)
        }
    }

    override fun addLocalTourPoint(tourPoint: TourPointEntity) {
        localRoutesDB.tourPointDao().addTourPoint(tourPoint)
    }

    override fun addLocalTourPointCategory(tourPointCategory: TourPointsCategoryEntity) {
        localRoutesDB.tourPointCategoryDao().addTourPointCategory(tourPointCategory)
    }
}