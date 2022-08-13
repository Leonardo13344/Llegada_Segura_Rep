package com.example.llegadasegura.Clases

import android.os.Parcel
import android.os.Parcelable

data class Grupo(
    val id:String??="",
    val rol: String?="",
    val nombre: String?="",
    val tipo: String?=""

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    companion object CREATOR : Parcelable.Creator<Grupo> {
        override fun createFromParcel(parcel: Parcel): Grupo {
            return Grupo(parcel)
        }

        override fun newArray(size: Int): Array<Grupo?> {
            return arrayOfNulls(size)
        }
    }
}