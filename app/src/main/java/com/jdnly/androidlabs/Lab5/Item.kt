package com.jdnly.androidlabs.Lab5

import android.os.Parcel
import android.os.Parcelable

class Item() : Parcelable {
    // Id
    var id: Int = 0
    // Тип
    var kind: String = ""
    // Название
    var title: String = ""
    // Стоимость
    var price: Double = 0.0
    // Вес
    var weight: Double = 0.0
    // Фото
    var photo: String = ""

    // Сводная информация
    val info: String
        get() = "$id $kind $title ($weight kg) ($price ₽)"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        kind = parcel.readString() ?: ""
        title = parcel.readString() ?: ""
        price = parcel.readDouble()
        weight = parcel.readDouble()
        photo = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(kind)
        parcel.writeString(title)
        parcel.writeDouble(price)
        parcel.writeDouble(weight)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
