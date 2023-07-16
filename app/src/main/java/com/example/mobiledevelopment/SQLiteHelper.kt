package com.example.mobiledevelopment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context?): SQLiteOpenHelper(context,"book.db", null, 1) {
	override fun onCreate(db: SQLiteDatabase?) {
		db?.execSQL("CREATE TABLE IF NOT EXISTS books (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"title TEXT, " +
				"genre TEXT, " +
				"author TEXT, " +
				"type_id INTEGER, " +
				"price DECIMAL, " +
				"rating DECIMAL, " +
				"photo TEXT)")
	}

	override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

	}
}