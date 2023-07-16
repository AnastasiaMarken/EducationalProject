package com.example.mobiledevelopment

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class LB4Activity : AppCompatActivity() {
    private lateinit var booksList: ListView
    private lateinit var fab: View

    private var books = ArrayList<Book>()
    private lateinit var db: SQLiteHelper
    private lateinit var con: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lb4)

        fab = findViewById(R.id.lb4_btn_add)
        booksList = findViewById(R.id.lb4_lv)

        db = SQLiteHelper(this)
        con = db.readableDatabase
        getBookItems()

        booksList.adapter = BookItemAdapter(this, books)

        booksList.setOnItemClickListener { adapterView: AdapterView<*>,
                                           view1: View, i: Int, l: Long ->
            val intent = Intent(this, LB4BookItemActivity::class.java)
            intent.putExtra("index", i)
            intent.putExtra("book", books[i])
            startActivityForResult(intent, 0)
        }

        fab.setOnClickListener {
            val intent = Intent(this, LB4BookItemActivity::class.java)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val index: Int = data?.getIntExtra("index", -1) ?: -1
            val item: Book = data?.getParcelableExtra("item") ?: Book()

            val cv = ContentValues()
            cv.put("title", item.title)
            cv.put("author", item.author)
            cv.put("type_id", item.type_index)
            cv.put("price", item.price)
            cv.put("rating", item.rating)
            cv.put("photo", item.photo)
            if (index != -1) {
                books[index] = item
                cv.put("id", item.id)
                con.update("books", cv, "id=?", arrayOf(item.id.toString()))
            }
            else {
                books.add(item)
                con.insert("books", null, cv)
            }

            val listView: ListView = findViewById(R.id.lb4_lv)
            (listView.adapter as BookItemAdapter).notifyDataSetChanged()
        }
    }

    private fun getBookItems(){
        val cursor = con.query("books",
            arrayOf("id", "title", "author", "type_id", "price", "rating", "photo"),
            null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val item = Book()
            item.id = cursor.getInt(0)
            item.title = cursor.getString(1)
            item.author = cursor.getString(2)
            item.type_index = cursor.getInt(3)
            item.price = cursor.getDouble(4)
            item.rating = cursor.getDouble(5)
            item.photo = cursor.getString(6)
            books.add(item)

            cursor.moveToNext()
        }
        cursor.close()
    }
}