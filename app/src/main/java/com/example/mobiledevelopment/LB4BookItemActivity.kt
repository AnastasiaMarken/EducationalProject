package com.example.mobiledevelopment

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File


class LB4BookItemActivity : AppCompatActivity() {
	private lateinit var btn_back: ImageView
	private lateinit var btn_save: ImageView
	private lateinit var book_cover: ImageView
	private lateinit var et_title: EditText
	private lateinit var et_author: EditText
	private lateinit var spin_type: Spinner
	private lateinit var et_price: EditText
	private lateinit var et_rating: EditText
	private lateinit var btn_camera: View

	private var index = -1
	private var currentPhotoPath: String = ""
	private lateinit var book: Book

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb4_book_item)

		val intent = intent
		index = intent?.getIntExtra("index", -1) ?: -1
		book = intent?.getParcelableExtra("book") ?: Book()

		btn_camera = findViewById(R.id.lb4_btn_camera)
		btn_back = findViewById(R.id.lb4_back)
		btn_save = findViewById(R.id.lb4_save)
		book_cover = findViewById(R.id.lb5_book_cover)
		et_title = findViewById(R.id.lb4_et_title)
		et_author = findViewById(R.id.lb4_et_author)
		spin_type = findViewById(R.id.lb4_spinner_type)
		et_price = findViewById(R.id.lb4_et_price)
		et_rating = findViewById(R.id.lb4_et_rating)


		et_title.setText(book.title)
		et_author.setText(book.author)
		spin_type.setSelection(book.type_index)
		et_price.setText(book.price.toString())
		et_rating.setText(book.rating.toString())
		currentPhotoPath = book.photo
		if (book.photo != "") {
			val bmp = BitmapFactory.decodeFile((book.photo))
			book_cover.setImageBitmap(bmp)
		}

		btn_back.setOnClickListener {
			finish()
		}

		btn_save.setOnClickListener {
			this.book.title = et_title.text.toString()
			this.book.author = et_author.text.toString()
			this.book.price = et_price.text.toString().toDouble()
			this.book.rating = et_rating.text.toString().toDouble()
			this.book.type_index = spin_type.selectedItemPosition
			this.book.photo = currentPhotoPath
			val intent = Intent()
			intent.putExtra("index", index)
			intent.putExtra("item", this.book)
			setResult(Activity.RESULT_OK, intent)

			finish()
		}

		btn_camera.setOnClickListener {
			val photoFile = File.createTempFile("photo", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
			currentPhotoPath = photoFile.absolutePath

			val photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, photoFile)

			val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
			startActivityForResult(intent, 0)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
		super.onActivityResult(requestCode, resultCode, intent)
		if (resultCode == Activity.RESULT_OK) {
			val bmp = BitmapFactory.decodeFile(currentPhotoPath)
			book_cover.setImageBitmap(bmp)
		} else currentPhotoPath = ""
	}
}