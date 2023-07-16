package com.example.mobiledevelopment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(val books: List<Book>, val context: Context): RecyclerView.Adapter<BookAdapter.ViewHolder>() {
	class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.model_list_item, parent, false)
				as LinearLayout
		return ViewHolder(view)
	}
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val titleView = holder.view.findViewById<TextView>(R.id.lb3_title)
		titleView.text = books[position].title
		val genreView = holder.view.findViewById<TextView>(R.id.lb3_genre)
		genreView.text = books[position].genre

		titleView.setOnClickListener {
			val toast: Toast = Toast.makeText(context,
				"Автор произведения " + books[position].title + ": " + books[position].author,
				Toast.LENGTH_SHORT)
			toast.show()
		}
	}
	override fun getItemCount(): Int {
		return books.size
	}
}