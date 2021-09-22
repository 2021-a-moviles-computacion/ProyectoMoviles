package com.example.myroom.objetos

import android.os.Parcel
import android.os.Parcelable

class Usuario (
    var id:String?=null,
    var nombre:String?=null,
    var apellido:String?=null,
    var correo:String?=null,
    var nacionalidad:String?=null,
    var genero:String?=null,
    var descripcion:String?=null,
    var lugaresVisitados:String?=null,
    var fotoPerfil:String?=null,
        ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(correo)
        parcel.writeString(nacionalidad)
        parcel.writeString(genero)
        parcel.writeString(descripcion)
        parcel.writeString(lugaresVisitados)
        parcel.writeString(fotoPerfil)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

}