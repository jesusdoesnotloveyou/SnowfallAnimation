package com.jdnly.androidlabs.lab7

class Coordinates(val latitude: Double, val longitude: Double)
{
    private var rangeDistance = 100

    fun getRangeDistance() : Int {
        return rangeDistance
    }

    fun calculateDistance(currentLatitude: Double, currentLongitude: Double) : Double {
        val EARTH_RADIUS = 6371221.0
        val cosD: Double = Math.sin(latitude) * Math.sin(currentLatitude) +
                Math.cos(latitude) * Math.cos(currentLatitude) * Math.cos(longitude - currentLongitude)
        return EARTH_RADIUS * Math.acos(cosD)
    }

    fun isPointNear(distance: Double) : Boolean {
        return (distance < rangeDistance)
    }
}