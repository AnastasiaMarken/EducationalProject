package com.example.mobiledevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LB1Activity : AppCompatActivity() {

	private lateinit var run_btn: Button
	private lateinit var et_num_1: EditText
	private lateinit var et_num_2: EditText
	private lateinit var tv_result: TextView


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb1)

		run_btn = findViewById(R.id.lb1_btn_sum)
		et_num_1 = findViewById(R.id.lb1_et_num_1)
		et_num_2 = findViewById(R.id.lb1_et_num_2)
		tv_result = findViewById(R.id.lb1_tv_result)


		run_btn.setOnClickListener {
			if (!et_num_1.text.isEmpty() && !et_num_2.text.isEmpty()) {
				val num_1 = et_num_1.text.toString().toFloat()
				val num_2 = et_num_2.text.toString().toFloat()

				val n = num_1 + num_2
				val result = resources.getString(R.string.lb1_result)
				tv_result.text = String.format(result, n)
			} else {
				tv_result.setText(resources.getString(R.string.lb1_no_result))
			}
		}
	}
}