package com.example.mobiledevelopment

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class BookItemAdapter(context: Context, items: ArrayList<Book>): BaseAdapter() {
	var ctx: Context = context
	var objects: ArrayList<Book> = items
	var inflater: LayoutInflater = ctx.
		getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

	override fun getView(position: Int, convertView: View?,
						 parent: ViewGroup?): View {

		var view = convertView
		if (view == null)
			view = inflater.inflate(R.layout.model_books_list,
				parent, false)

		val book = objects[position]

		var v = view?.findViewById(R.id.lb4_title) as TextView
		v.text = book.title

		v = view.findViewById(R.id.lb4_author) as TextView
		v.text = book.author

		v = view.findViewById(R.id.lb4_rating) as TextView
		v.text = book.rating.toString()

		v = view.findViewById(R.id.lb4_price) as TextView
		v.text = book.price_info

		v = view.findViewById(R.id.lb4_type) as TextView
		val types = ctx.resources.getStringArray(R.array.lb4_book_types)
		v.text = types.get(book.type_index)
		if (book.type_index == 1) {
			v.background = ctx.resources.getDrawable(R.drawable.shape_book_type_2)
		} else v.background = ctx.resources.getDrawable(R.drawable.shape_book_type)

		if (book.photo != "") {
			val bmp = BitmapFactory.decodeFile(book.photo)
			view.findViewById<ImageView>(R.id.lb5_book_img).setImageBitmap(bmp)

		} else view.findViewById<ImageView>(R.id.lb5_book_img).setImageDrawable(ctx.resources.getDrawable(R.drawable.ic_book_dark))

		return view
	}

	override fun getItem(position: Int): Any {
		return objects[position]
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getCount(): Int {
		return objects.size
	}
}
