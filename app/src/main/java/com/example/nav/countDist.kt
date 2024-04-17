package com.example.nav



class countDist(val x: String , val y: String) {

    fun getSplitX(): List<String> {
        val spl = ','
        return x.split(spl)
    }
    fun getSplitY(): List<String> {
        val spl = ','
        return y.split(spl)
    }
    val llArrX:List<String> = getSplitX()
    val llArrY:List<String> = getSplitY()
    val latitudeX: Double = llArrX[0].toDouble()
    val longtitudeX: Double= llArrX[1].toDouble()
    val latitudeY: Double = llArrY[0].toDouble()
    val longtitudeY: Double = llArrY[1].toDouble()
    fun getCalculateS(): Double {
        return (Math.acos((Math.sin(latitudeX)*Math.sin(latitudeY))+(Math.cos(latitudeX)*Math.cos(latitudeY)*Math.cos(longtitudeX-longtitudeY)))*63710).toDouble()
    }
}