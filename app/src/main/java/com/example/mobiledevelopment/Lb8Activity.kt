package com.example.mobiledevelopment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class Lb8Activity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb8)
	}

	fun buttonShare(view: View) {
		val file = File.createTempFile("share", ".jpg",
			getExternalFilesDir(Environment.DIRECTORY_PICTURES))
		val drawTree = findViewById<DrawView>(R.id.draw_tree)
		val b = drawTree.getBitmap()
		b!!.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file));

		val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file)
		val share = Intent(Intent.ACTION_SEND)
		share.type = "image/jpeg"
		share.putExtra(Intent.EXTRA_STREAM, uri)
		startActivity(Intent.createChooser(share, "Отправить"))
	}
}