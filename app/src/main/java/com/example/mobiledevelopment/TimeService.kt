package com.example.mobiledevelopment

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeService : Service() {
	private var counter = 0
	private var startCounter = 0
	private lateinit var job: Job
	private val myBinder = MyBinder()
	private var interval = 1000
	var receiver: BroadcastReceiver? = null


	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		job = GlobalScope.launch {
			while (true) {
				val intent = Intent(BROADCAST_TIME_EVENT)
				delay(interval.toLong())
				Log.d("SERVICE", "Timer Is Ticking: " + counter)
				counter++
				intent.putExtra("counter", counter)
				intent.putExtra("interval", interval)
				intent.putExtra("startCount", startCounter)
				sendBroadcast(intent)

				receiver = object : BroadcastReceiver() {
					// Получено широковещательное сообщение
					override fun onReceive(context: Context?, intent: Intent) {
						interval = intent.getIntExtra("interval", interval)
						if (intent.hasExtra("startCounter")){
							startCounter = intent.getIntExtra("startCounter", 0)
							counter = startCounter
						}
					}
				}
				// Фильтр для ресивера
				val filter = IntentFilter(BROADCAST_TIME_EVENT)
				// Регистрация ресивера и фильтра
				registerReceiver(receiver, filter)
			}
		}
		return super.onStartCommand(intent, flags, startId)
	}
	override fun onDestroy() {
		Log.d("SERVICE", "onDestroy")
		job.cancel()
		super.onDestroy()
	}

	fun getCounter(): Int {
		return counter
	}

	override fun onBind(intent: Intent?): IBinder? {
		return myBinder
	}
	inner class MyBinder : Binder() {
		fun getService() : TimeService {
			return this@TimeService
		}
	}
}