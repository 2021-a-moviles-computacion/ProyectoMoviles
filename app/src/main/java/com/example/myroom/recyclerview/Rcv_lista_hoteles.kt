package com.example.myroom.recyclerview

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.objetos.HotelHabitacion

class Rcv_lista_hoteles (
    private val context:Class<*>,
    private val recyclerView: RecyclerView ,
    private val list: List<HotelHabitacion>
        ):RecyclerView.Adapter<Rcv_lista_hoteles.myViewHolder>(){
            inner class myViewHolder(view: View):RecyclerView.ViewHolder(view){
                val fotoMiniHotel: ImageView
                val nombreHotel:TextView
                val direccion:TextView
                val puntuacion:TextView
                val nombreHabitacion:TextView
                val numeroCamas:TextView
                val numeroAdultos: TextView
                val precio:TextView
                val tipoPago:TextView
                init {
                    fotoMiniHotel= view.findViewById<ImageView>(R.id.img_hotelLista)

                     nombreHotel= view.findViewById<TextView>(R.id.txt_nombre_hotelLista)
                     direccion= view.findViewById<TextView>(R.id.txt_direccionHotelLista)
                     puntuacion= view.findViewById<TextView>(R.id.txt_puntuacionHotelLista)
                     nombreHabitacion= view.findViewById<TextView>(R.id.txt_habitacionLista)
                     numeroCamas= view.findViewById<TextView>(R.id.txt_numeroCamasLista)
                     numeroAdultos= view.findViewById<TextView>(R.id.txt_adultosNinosHotelLista)
                     precio= view.findViewById<TextView>(R.id.txt_precioHotelLista)
                     tipoPago= view.findViewById<TextView>(R.id.txt_tipoPagoLista)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rcv_hoteles,parent,false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val hotelHabitacion = list[position]
        holder.fotoMiniHotel.setImageBitmap(BitmapFactory.decodeByteArray(hotelHabitacion.imagen,0,hotelHabitacion.imagen!!.size))
        //holder.fotoMiniHotel.scaleType=ImageView.ScaleType.CENTER_CROP
        holder.nombreHotel.text=hotelHabitacion.nombreHotel
        holder.direccion.text=hotelHabitacion.direccion
        holder.puntuacion.text=hotelHabitacion.puntuacion
        holder.nombreHabitacion.text=hotelHabitacion.nombreHabitacion
        holder.numeroCamas.text=hotelHabitacion.numeroCamas
        holder.numeroAdultos.text=hotelHabitacion.numeroAdultos
        holder.precio.text="$ "+String.format("%.2f",hotelHabitacion.precio)
        holder.tipoPago.text=hotelHabitacion.tipoPago
    }

    override fun getItemCount(): Int {
        return list.size
    }
}