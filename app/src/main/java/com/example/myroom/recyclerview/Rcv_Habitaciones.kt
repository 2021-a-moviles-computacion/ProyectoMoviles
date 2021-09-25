package com.example.myroom.recyclerview

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.Hotel
import com.example.myroom.R
import com.example.myroom.TipoDeHabitacion
import com.example.myroom.objetos.Habitaciones
import com.example.myroom.objetos.HotelHabitacion
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.Format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Rcv_Habitaciones(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val list: ArrayList<Habitaciones>
): RecyclerView.Adapter<Rcv_Habitaciones.myViewHolder>(){
    inner class myViewHolder(view: View): RecyclerView.ViewHolder(view){
        var fotoHabitacion:ImageView
        val nombre:TextView
        val numeroCamas:TextView
        val servicio1:TextView
        val servicio2:TextView
        val servicio3:TextView
        val numeroHabitacionesSeleccionadas:TextView
        val numeroDeAdultosSeleccionado:TextView
        val numeroDeNinSeleccionado:TextView
        val fechaEntrada:TextView
        val fechaSalida:TextView
        val precioCalculado:TextView
        val botonAdd:TextView
        // aumentar y disminuir
        val btnMenosHabs:TextView
        val btnMasHabs:TextView

        val btnMenosAdul:TextView
        val btnMasAdul:TextView

        val btnMenosNin:TextView
        val btnMasNin:TextView
        //val Imagenes =ArrayList<ByteArray>()
        //val adapter: Rcv_Imagenes_Horizontales

        init {

            nombre= view.findViewById(R.id.txv_tipoHabitacionLista)
            fotoHabitacion=view.findViewById(R.id.img_tipo_habitacion)
            numeroCamas=view.findViewById(R.id.txt_primerServicioLista)
            servicio1=view.findViewById(R.id.txt_segundoServicioLista)
            servicio2=view.findViewById(R.id.txt_tercerServicioLista)
            servicio3=view.findViewById(R.id.txt_cuartoServicioLista)
            numeroHabitacionesSeleccionadas=view.findViewById(R.id.txt_numHabitaciones)
            numeroDeAdultosSeleccionado=view.findViewById(R.id.txt_numAdultos)
            numeroDeNinSeleccionado=view.findViewById(R.id.txt_numNinos)
            fechaEntrada=view.findViewById(R.id.txt_fechaEntrada)
            fechaSalida=view.findViewById(R.id.txt_fechaSalida)
            precioCalculado=view.findViewById(R.id.txt_PrecioHabitacion)
            botonAdd=view.findViewById(R.id.txt_btn_AgregarHabitacion)

            btnMenosHabs=view.findViewById(R.id.txt_btn_menosNumHabitaciones)
            btnMasHabs=view.findViewById(R.id.txt_btn_masNumHabitaciones)

            btnMenosAdul=view.findViewById(R.id.txt_btn_menosNumAdultos)
            btnMasAdul=view.findViewById(R.id.txt_btn_masNumAdultos)

            btnMenosNin=view.findViewById(R.id.txt_btn_menosNumNinos)
            btnMasNin=view.findViewById(R.id.txt_btn_masNumNinos)
            //adapter= Rcv_Imagenes_Horizontales(context,fotosHabitaciones,Imagenes)




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rcv_lista_habitaciones,parent,false)
        return myViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val habitacion = list[position]
        holder.nombre.text=habitacion.nombre
        holder.numeroCamas.text=habitacion.numeroCamas.toString()
        holder.servicio1.text= habitacion.servicios?.get(0)
        holder.servicio2.text= habitacion.servicios?.get(1)
        holder.servicio3.text= habitacion.servicios?.get(2)
        holder.numeroHabitacionesSeleccionadas.text="1"
        holder.numeroDeAdultosSeleccionado.text=habitacion.numeroMinAdultos.toString()
        holder.numeroDeNinSeleccionado.text="0"
        //var formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        holder.fechaEntrada.text="ahora perro"
        holder.fechaSalida.text="maniana mi so"
        //LocalDate.parse( holder.fechaEntrada.text,formato)
       // LocalDate.parse(holder.fechaSalida.text,formato).plusDays(1)
        //habitaciones
        holder.btnMenosHabs.setOnClickListener {
            if(holder.numeroHabitacionesSeleccionadas.text.toString().toInt()<=0){
                Toast.makeText(context,"Numero Minimo de Habitaciones Permitido",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroHabitacionesSeleccionadas.text= (holder.numeroHabitacionesSeleccionadas.text.toString().toInt()-1).toString()
            }
        }
        holder.btnMasHabs.setOnClickListener {
            if(holder.numeroHabitacionesSeleccionadas.text.toString().toInt()>habitacion.numeroHabitaciones!!){
                Toast.makeText(context,"Numero Maximo de Habitaciones Disponibles",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroHabitacionesSeleccionadas.text= (holder.numeroHabitacionesSeleccionadas.text.toString().toInt()+1).toString()
            }
        }
        //Adultos
        holder.btnMenosAdul.setOnClickListener {
            if(holder.numeroDeAdultosSeleccionado.text.toString().toInt()<=habitacion.numeroMinAdultos!!){
                Toast.makeText(context,"Numero Minimo de Adultos Permitido",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroDeAdultosSeleccionado.text= (holder.numeroDeAdultosSeleccionado.text.toString().toInt()-1).toString()
            }
        }
        holder.btnMasAdul.setOnClickListener {
            if(holder.numeroDeAdultosSeleccionado.text.toString().toInt()>habitacion.numeroMaxAdultos!!){
                Toast.makeText(context,"Numero Maximo de Adultos Permitido",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroDeAdultosSeleccionado.text= (holder.numeroDeAdultosSeleccionado.text.toString().toInt()+1).toString()
            }
        }
        //Niños
        holder.btnMenosNin.setOnClickListener {
            if(holder.numeroDeNinSeleccionado.text.toString().toInt()<0){
                Toast.makeText(context,"Numero Minimo seleccionado",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroDeNinSeleccionado.text= (holder.numeroDeNinSeleccionado.text.toString().toInt()-1).toString()
            }
        }
        holder.btnMasNin.setOnClickListener {
            if(holder.numeroDeNinSeleccionado.text.toString().toInt()>habitacion.numeroMaxNin!!){
                Toast.makeText(context,"Numero Maximo de Niños para la habitación",Toast.LENGTH_SHORT).show()
            }
            else{
                holder.numeroDeNinSeleccionado.text= (holder.numeroDeNinSeleccionado.text.toString().toInt()+1).toString()
            }
        }



        val reference= Firebase.storage.reference
        reference.child("TiposHabitaciones/"+habitacion.id+"/1.jpg").listAll()
            .addOnSuccessListener {

                if(it.items.size!=0) {
                    it.items[0].getBytes(1024 * 1024 * 3).addOnSuccessListener {
                        Log.i("storage", "Consulta de img Habitacion")
                        holder.fotoHabitacion.setImageBitmap(
                            BitmapFactory.decodeByteArray(
                                it,
                                0,
                                it.size
                            )
                        )


                    }
                        .addOnFailureListener {
                            Log.i("recyclerView", "error ${it.message}")

                        }
                }
            }





    }



    override fun getItemCount(): Int {
        return list.size
    }
}