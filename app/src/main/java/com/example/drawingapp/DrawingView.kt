package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt

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

    private val paths = mutableListOf<FingerPath>()

    init {
        setUpDrawing()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasBitmap = createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(canvasBitmap, 0f, 0f, drawPaint)

        paths.forEach { path ->
            drawPaint.apply {
                strokeWidth = path.brushThickness
                color = path.color
            }

            canvas.drawPath(path, drawPaint)
        }

        if (!drawPath.isEmpty) {
            drawPaint.apply {
                strokeWidth = drawPath.brushThickness
                color = drawPath.color
            }

            canvas.drawPath(drawPath, drawPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> drawPath.apply {
                color = brushColor
                brushThickness = brushSize
                reset()
                rMoveTo(touchX!!, touchY!!)

            }

            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX!!, touchY!!)

            MotionEvent.ACTION_UP -> {
                paths.add(drawPath)
                drawPath = FingerPath(brushColor, brushSize)
            }

            else -> return false
        }

        invalidate()

        return true
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

    fun changeBrushSize(newSize: Float) {
        brushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize,
            resources.displayMetrics
        )
        drawPaint.strokeWidth = brushSize
    }

    fun setBrushColor(newColor: String) {
        brushColor = newColor.toColorInt()
        drawPaint.color = brushColor
    }

    internal inner class FingerPath(var color: Int, var brushThickness: Float) : Path()
}
