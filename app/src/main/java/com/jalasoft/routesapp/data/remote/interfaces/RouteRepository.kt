package com.jalasoft.routesapp.data.remote.interfaces

import android.content.Context
import com.jalasoft.routesapp.data.model.local.LineCategoriesEntity
import com.jalasoft.routesapp.data.model.local.LineEntity
import com.jalasoft.routesapp.data.model.remote.LinePath
import com.jalasoft.routesapp.data.model.remote.LineRoutePath

interface RouteRepository {
    suspend fun getAllLines(context: Context): List<LinePath>
    suspend fun getAllLinesToSaveLocally(context: Context): List<LineEntity>
    suspend fun getAllLinesCategoryToSaveLocally(): List<LineCategoriesEntity>
    suspend fun getAllLinesRouteToSaveLocally(idLine: String): List<LineRoutePath>
}
