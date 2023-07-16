package com.example.mobiledevelopment

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView


const val BROADCAST_TIME_EVENT = "com.example.mobiledevelopment.timeevent"
class LB10Activity : AppCompatActivity() {
	var myService: TimeService? = null
	var isBound = false
	val myConnection = object : ServiceConnection {
		override fun onServiceConnected(className: ComponentName, service: IBinder) {
			val binder = service as TimeService.MyBinder
			myService = binder.getService()
			isBound = true
		}
		override fun onServiceDisconnected(name: ComponentName) {
			isBound = false
		}

	}
	var receiver: BroadcastReceiver? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb10)

		receiver = object : BroadcastReceiver() {
			// Получено широковещательное сообщение
			override fun onReceive(context: Context?, intent: Intent) {
				val counter = intent.getIntExtra("counter", 0)
				val textCounter = findViewById<TextView>(R.id.lb10_tv_get_count)
				textCounter.text = "Count: $counter"

				val interval = intent.getIntExtra("interval", 0)
				val countInterval = findViewById<TextView>(R.id.lb10_tv_interval)
				countInterval.text = "Interval: $interval"

				val startCount = intent.getIntExtra("startCount", 0)
				val textStartCount = findViewById<TextView>(R.id.lb10_tv_start_counter)
				textStartCount.text = "Start count: $startCount"
				findViewById<TextView>(R.id.lb10_ed_count).text = startCount.toString()
			}
		}
		// Фильтр для ресивера
		val filter = IntentFilter(BROADCAST_TIME_EVENT)
		// Регистрация ресивера и фильтра
		registerReceiver(receiver, filter)
	}

	override fun onDestroy() {
		// Удаление регистрации ресивера и фильтра
		unregisterReceiver(receiver);
		super.onDestroy()
	}

	fun buttonStartService(view: View) {
		startService(Intent(this, TimeService::class.java))
		bindService(Intent(this, TimeService::class.java),
			myConnection, Context.BIND_AUTO_CREATE)
	}
	fun buttonStopService(view: View) {
		unbindService(myConnection)
	}

	fun buttonGetValue(view: View) {
		if (isBound)
			findViewById<TextView>(R.id.lb10_tv_get_count).text =
				myService!!.getCounter().toString()
	}

	fun buttonSetInterval(view: View) {
		val interval = findViewById<TextView>(R.id.lb10_ed_interval).text.toString().toInt()
		val intent = Intent(BROADCAST_TIME_EVENT)
		intent.putExtra("interval", interval)
		sendBroadcast(intent)
	}
	fun buttonSetCount(view: View) {
		val intent = Intent(BROADCAST_TIME_EVENT)
		if (view == findViewById(R.id.lb10_set_count)){
			val startCounter = findViewById<TextView>(R.id.lb10_ed_count).text.toString().toInt()
			intent.putExtra("startCounter", startCounter)
		} else intent.putExtra("startCounter", 0)
		sendBroadcast(intent)
	}
}