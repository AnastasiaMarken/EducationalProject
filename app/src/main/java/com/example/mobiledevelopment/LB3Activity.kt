package com.example.mobiledevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class LB3Activity : AppCompatActivity() {
	private lateinit var lv_authors: ListView
	private lateinit var lv_books: ListView
	private lateinit var lv_ww: RecyclerView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb3)

		lv_authors = findViewById(R.id.lb3_lv_authors)
		lv_books = findViewById(R.id.lb3_lv_books)
		lv_ww = findViewById(R.id.lb3_rv_ww)

		val authors = arrayOf("Дж. К. Роулинг", "Роберт Гэлбрейт", "Это один человек..")
		val books = arrayOf("Зов кукушки", "Да здравствует фикус!", "Ярмарка тщеславия")
		val all_books = ArrayList<Book>()
//		all_books.add(Book("Дурная кровь", "Детектив", "Роберт Гэлбрейт"))
//		all_books.add(Book("Чума", "Абсурдизм", "Альбер Камю"))
//		all_books.add(Book("Алхимик", "Приключения", "Пауло Коэльо"))

		lv_authors.choiceMode = ListView.CHOICE_MODE_SINGLE
		lv_books.choiceMode = ListView.CHOICE_MODE_MULTIPLE

		lv_authors.adapter = ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_single_choice, authors)
		lv_books.adapter = ArrayAdapter<String>(this,
			 android.R.layout.simple_list_item_multiple_choice, books)
		lv_ww.adapter = BookAdapter(all_books, this)
	}
}