package com.example.mobiledevelopment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView : View {
	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

	private var w: Float = 0f // Ширина представления
	private var h: Float = 0f // Высота представления
	private var groundY: Float = 0f // Горизонт
	private var treeWidth: Float = 0f // Ширина дерева
	private var treeHeight: Float = 0f // Высота дерева
	private var crownDiameter: Float = 0f // Диаметр кроны
	private var crownX: Float = 0f // Центр кроны по X
	private var crownY: Float = 0f // Центр кроны по Y

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)
		this.w = w.toFloat()
		this.h = h.toFloat()
		groundY = this.h - this.h / 3
		treeWidth = this.w / 12
		treeHeight = this.h / 6
		crownDiameter = this.w / 5
		crownX = this.w / 2
		crownY = groundY - treeHeight - crownDiameter
	}


	private var paint = Paint()
	private var _season: Int = 0
	var season: Int
		get() = this._season
		set(value) {
			this._season = value
			invalidate()
		}
	override fun onDraw(canvas: Canvas?) {
		// Небо – это фон элемента управления
		canvas!!.drawColor(Color.argb(255,171, 216, 229))
		// Земля
		paint.color = if (_season == 0) Color.argb(255,87, 166, 99)
			else Color.argb(255,185, 93, 45)
		canvas.drawRect(0f, groundY, w, h, paint)
		// Ствол дерева
		paint.color = Color.argb(255,59, 40, 33)
		canvas.drawRect(w / 2 - treeWidth / 2, groundY - treeHeight,
			w / 2 + treeWidth / 2, groundY, paint)
		// Крона
		paint.color = if (_season == 0) Color.argb(255,109, 191, 75)
			else Color.argb(255,227, 123, 2)
		canvas.drawCircle(crownX, crownY, crownDiameter, paint)
	}

	fun getBitmap(): Bitmap? {
		var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
		var canvas = Canvas(bitmap)
		draw(canvas)
		return bitmap
	}
}