package com.example.mobiledevelopment

import android.os.Parcel
import android.os.Parcelable

class Book() : Parcelable{
	var id: Int = 0
	var title: String = ""
	var genre: String = ""
	var author: String = ""
	var type_index: Int = 0
	var price: Double = 0.0
	var rating: Double = 0.0
	var photo: String = ""
	val price_info: String
		get() = "$price ₽"


	constructor(parcel: Parcel) : this() {
		id = parcel.readInt()
		title = parcel.readString() ?: ""
		genre = parcel.readString() ?: ""
		author = parcel.readString() ?: ""
		type_index = parcel.readInt()
		price = parcel.readDouble()
		rating = parcel.readDouble()
		photo = parcel.readString() ?: ""
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(title)
		parcel.writeString(genre)
		parcel.writeString(author)
		parcel.writeInt(type_index)
		parcel.writeDouble(price)
		parcel.writeDouble(rating)
		parcel.writeString(photo)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Book> {
		override fun createFromParcel(parcel: Parcel): Book {
			return Book(parcel)
		}

		override fun newArray(size: Int): Array<Book?> {
			return arrayOfNulls(size)
		}
	}
}