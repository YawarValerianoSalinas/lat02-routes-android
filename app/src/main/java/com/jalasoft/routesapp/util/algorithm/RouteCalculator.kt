package com.jalasoft.routesapp.util.algorithm

import android.location.Location
import com.jalasoft.routesapp.data.model.remote.AvailableTransport
import com.jalasoft.routesapp.data.model.remote.LinePath
import com.jalasoft.routesapp.data.model.remote.LinesCandidate

object RouteCalculator {
    fun calculate(lines: List<LinePath>, destination: Location, origin: Location, minDistance: Double, minDistanceBtwStops: Double): MutableList<AvailableTransport> {
        val availableTransport: MutableList<AvailableTransport> = mutableListOf()

        val candidates = LinesCandidate()

        for (line in lines) {
            val nearestStopToOrigin = line.stops.minWith(Comparator.comparingDouble { it.distanceTo(origin).toDouble() })
            val nearestStopToDestination = line.stops.minWith(Comparator.comparingDouble { it.distanceTo(destination).toDouble() })

            if (nearestStopToDestination.distanceTo(destination).toDouble() <= minDistance && nearestStopToOrigin.distanceTo(origin).toDouble() <= minDistance) {
                LinePath.getOneRouteLine(line, nearestStopToDestination, nearestStopToOrigin)?.let {
                    availableTransport.add(it)
                }
            } else {
                getCandidateLine(nearestStopToDestination, destination, minDistance, line, true)?.let {
                    candidates.destinationList.add(it)
                }

                getCandidateLine(nearestStopToOrigin, origin, minDistance, line, false)?.let {
                    candidates.originList.add(it)
                }
            }
        }

        for (routeFromOrigin in candidates.originList) {
            for (routeFromDestiny in candidates.destinationList) {
                for (stop in routeFromDestiny.stops) {
                    joinTwoLinesWithNearStopPoints(routeFromOrigin, routeFromDestiny, stop, minDistanceBtwStops)?.let {
                        availableTransport.add(it)
                    }
                }
            }
        }
        return availableTransport
    }

    private fun joinTwoLinesWithNearStopPoints(routeFromOrigin: LinePath, routeFromDestiny: LinePath, stop: Location, minDistanceBtwStops: Double): AvailableTransport? {
        val nearestStop = routeFromOrigin.stops.minWith(Comparator.comparingDouble { it.distanceTo(stop).toDouble() })
        if (nearestStop.distanceTo(stop).toDouble() <= minDistanceBtwStops) {
            val indexOfNearestStop = LinePath.getIndexOfFromLocationList(nearestStop, routeFromOrigin.stops)
            // Line A
            val lineA = LinePath.getSubLine(routeFromOrigin, nearestStop, true)
            // Line B
            val lineB = LinePath.getSubLine(routeFromDestiny, stop, false)
            return AvailableTransport(indexOfNearestStop, mutableListOf(lineA, lineB))
        }
        return null
    }

    private fun getCandidateLine(nearestStop: Location, pointToCheck: Location, minDistance: Double, line: LinePath, sliceFromStart: Boolean): LinePath? {
        if (nearestStop.distanceTo(pointToCheck).toDouble() <= minDistance) {
            return LinePath.getSubLine(line, nearestStop, sliceFromStart)
        }
        return null
    }
}