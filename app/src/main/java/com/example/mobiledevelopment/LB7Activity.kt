package com.example.mobiledevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class LB7Activity : AppCompatActivity() {
	private lateinit var etQuery: EditText
	private lateinit var listView: ListView
	private lateinit var txtResultCount: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb7)

		etQuery = findViewById(R.id.lb7_query)
		listView = findViewById(R.id.lb7_list)
		txtResultCount = findViewById(R.id.lb7_result_count)
	}

	fun buttonSearchClick(view: View) {
		val s: String = etQuery.text.toString()

		GlobalScope.launch{
			val url = URL("https://api.github.com/search/repositories?q=${s}").readText()
			MainScope().launch {
				val json = JSONObject(url)
				val count = json.getInt("total_count")
				val array = json.getJSONArray("items")
				val links : MutableList<String> = mutableListOf()
				for (i in 0 until array.length()) {
					val o = array.getJSONObject(i)
					val name = o.getString("name")
					links.add(name)
				}
				txtResultCount.text= "Результатов: $count"
				txtResultCount.visibility = View.VISIBLE
				val adapter = ArrayAdapter<String>(this@LB7Activity, android.R.layout.simple_list_item_1, links)
				listView.adapter = adapter
			}
		}
	}
}