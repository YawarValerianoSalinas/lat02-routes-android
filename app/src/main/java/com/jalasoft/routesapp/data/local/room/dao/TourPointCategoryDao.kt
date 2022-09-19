package com.jalasoft.routesapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jalasoft.routesapp.data.model.local.TourPointsCategoryEntity

@Dao
interface TourPointCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTourPointCategory(tourPointsCategory: TourPointsCategoryEntity)

    @Query("SELECT * FROM tourpointscategoryentity")
    fun getAllTourPointsCategory(): List<TourPointsCategoryEntity>
}