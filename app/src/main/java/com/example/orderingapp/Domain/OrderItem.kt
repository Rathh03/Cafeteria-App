package com.example.orderingapp.Domain

import android.os.Parcel
import android.os.Parcelable

data class OrderItem(
    val id: Int = 0,
    val name: String = "",
    val picUrl: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(picUrl)
        parcel.writeDouble(price)
        parcel.writeInt(quantity)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<OrderItem> {
        override fun createFromParcel(parcel: Parcel) = OrderItem(parcel)
        override fun newArray(size: Int) = arrayOfNulls<OrderItem?>(size)
    }
}
