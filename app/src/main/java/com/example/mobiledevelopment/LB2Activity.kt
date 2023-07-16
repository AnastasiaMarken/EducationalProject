package com.example.mobiledevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginLeft

class LB2Activity : AppCompatActivity() {

	private lateinit var add_tv_btn: Button
	private var viewsCount = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb2)
		Log.i("LogInfo", "Метод onCreate")

		add_tv_btn = findViewById(R.id.lb2_btn)

		add_tv_btn.setOnClickListener {
//			addTextView()
			addButton()
		}

		if (savedInstanceState != null) {
			val count = savedInstanceState.getInt("viewsCount")
			for (i in 0 until count)
				addTextView()
//				addButton()
		}
	}

	fun addTextView(){
		val textView = TextView(this)
		viewsCount++
		textView.text = viewsCount.toString()
		textView.setTextAppearance(R.style.TextMain2Style)
		val container = findViewById<LinearLayout>(R.id.lb2_linear_l)
		container.addView(textView)
	}

	fun addButton() {
		val new_btn = Button(this)
		viewsCount++
		new_btn.text = "Кнопка " + viewsCount.toString()
		new_btn.tag = viewsCount
		new_btn.setOnClickListener {
			val toast = Toast.makeText(this,
				"Нажата кнопка ${ it.tag }",
				Toast.LENGTH_SHORT)
			toast.show();
		}
		val container: ViewGroup = findViewById(R.id.lb2_linear_l)
		container.addView(new_btn)
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putInt("viewsCount", viewsCount)
	}

	override fun onStart() {
		super.onStart()
		Log.i("LogInfo", "Метод onStart")
	}

	override fun onResume() {
		super.onResume()
		Log.i("LogInfo", "Метод onResume")
	}

	override fun onPause() {
		super.onPause()
		Log.i("LogInfo", "Метод onPause")
	}

	override fun onStop() {
		super.onStop()
		Log.i("LogInfo", "Метод onStop")
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.i("LogInfo", "Метод onDestroy")
	}
}