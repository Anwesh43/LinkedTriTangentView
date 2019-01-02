package com.anwesh.uiprojects.tritangentstepview

/**
 * Created by anweshmishra on 02/01/19.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Path
import android.app.Activity
import android.content.Context

val nodes : Int = 5
val lines : Int = 3
val scGap : Float = 0.05f
val scDiv : Double = 0.51
val strokeFactor : Int = 90
val sizeFactor : Int = 3
val foreColor : Int = Color.parseColor("#283593")
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.mirrorValue(a : Int, b : Int) : Float = (1 - scaleFactor()) * a.inverse() + scaleFactor() * b.inverse()
fun Float.updateScale(dir : Float, a : Int, b : Int) : Float = mirrorValue(a, b) * scGap * dir

fun Canvas.drawTriangle(size : Float, paint : Paint) {
    val deg : Float = 360f / lines
    var startDeg : Float = deg/4
    val path = Path()
    for (j in 0..(lines - 1)) {
        var currDeg : Float = deg * j + startDeg
        val x : Float = size * Math.cos(currDeg * Math.PI/180).toFloat()
        val y : Float = size * Math.sin(currDeg * Math.PI/180).toFloat()
        if (j == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    drawPath(path, paint)
}

fun Canvas.drawTangent(size : Float, scale : Float, paint : Paint) {
    val deg : Float = 360f / lines
    for (j in 0..(lines - 1)) {
        val sc : Float = scale.divideScale(j, lines)
        save()
        rotate(deg * j)
        drawLine(-size * sc, -size, size * sc, -size, paint)
        restore()
    }
}

fun Canvas.drawTTSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    val size : Float = gap / sizeFactor
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    paint.color = foreColor
    save()
    translate(gap * (i + 1), h/2)
    rotate(180f * sc2)
    drawTriangle(size, paint)
    drawTangent(size, scale, paint)
    restore()
}