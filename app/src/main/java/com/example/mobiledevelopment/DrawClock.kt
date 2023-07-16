package com.example.mobiledevelopment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class DrawClock : SurfaceView, SurfaceHolder.Callback {
	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?,
				defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
	override fun surfaceChanged(holder: SurfaceHolder, format: Int,
								width: Int, height: Int) {
	}

	private var paint = Paint()
	private lateinit var job: Job
	private var centerx = 0f
	private var centery = 0f
	private var border_radius = 0f
	private var clock_radius = 0f
	private var length_second = 0f
	private var length_minutes = 0f
	private var length_hours = 0f
	private var angle: Double = PI / 180
	private var hours_grad = Date().hours * 6 * angle
	private var minutes_gard = Date().minutes * 6 * angle
	private var seconds_grad = Date().seconds * 6 * angle

	override fun surfaceCreated(holder: SurfaceHolder) {
		centerx = width.toFloat() / 2
		centery = height.toFloat() / 2
		border_radius = (min(centery, centerx) * 0.8).toFloat()
		clock_radius = (border_radius * 0.9).toFloat()

		length_second = (clock_radius * 0.8).toFloat()
		length_minutes = (clock_radius * 0.7).toFloat()
		length_hours = (clock_radius * 0.55).toFloat()

		job = GlobalScope.launch {
			var canvas: Canvas?
			while (true) {
				canvas = holder.lockCanvas(null)
				if (canvas != null) {
					//фон
					canvas.drawColor(Color.argb(255,241, 240, 249))
					//рамка
					paint.color = Color.rgb(80, 60, 82)
					canvas.drawCircle(centerx, centery, border_radius, paint)
					//циферблат
					paint.color = Color.rgb(248, 255, 251)
					canvas.drawCircle(centerx, centery, clock_radius, paint)
					//секундная
					paint.color = Color.rgb(80, 60, 82)
					paint.strokeWidth = 10f
					seconds_grad = Date().seconds * 6 * angle
					canvas.drawLine(centerx, centery, centerx + length_second * cos(angle * 90 - seconds_grad).toFloat(),
						centery - length_second * sin(angle * 90 - seconds_grad).toFloat(), paint)
					//минутная
					paint.strokeWidth = 14f
					minutes_gard = Date().minutes * 6 * angle
					canvas.drawLine(centerx, centery, centerx + length_minutes * cos(angle * 90 - minutes_gard).toFloat(),
						centery - length_minutes * sin(angle * 90 - minutes_gard).toFloat(), paint)
					//часовая
					paint.strokeWidth = 19f
					hours_grad = Date().hours * 30 * angle
					canvas.drawLine(centerx, centery, centerx + length_hours * cos(angle * 90 - hours_grad).toFloat(),
						centery - length_hours * sin(angle * 90 - hours_grad).toFloat(), paint)

					holder.unlockCanvasAndPost(canvas)
				}
			}
		}
	}
	override fun surfaceDestroyed(holder: SurfaceHolder) {
		job.cancel()
	}
	init {
		holder.addCallback(this)
	}
}