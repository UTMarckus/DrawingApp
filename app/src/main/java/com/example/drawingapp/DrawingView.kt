package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // drawing path
    private lateinit var drawPath: FingerPath
    // defines what to draw
    private lateinit var canvasPaint: Paint
    // defines how to draw
    private lateinit var drawPaint: Paint
    private lateinit var canvas: Canvas
    private lateinit var canvasBitmap: Bitmap

    private var brushColor = Color.BLACK
    private var brushSize = 0F

    init {
        setUpDrawing()
    }


    private fun setUpDrawing() {
        drawPaint = Paint().apply {
            color = brushColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        drawPath = FingerPath(brushColor, brushSize)
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20F
    }

    internal inner class FingerPath(val color: Int, brushThickness: Float) : Path() {

    }
}
