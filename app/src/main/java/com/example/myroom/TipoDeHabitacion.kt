package com.example.myroom

import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.objetos.Habitaciones
import com.example.myroom.recyclerview.Rcv_Habitaciones
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TipoDeHabitacion : AppCompatActivity() {
    val db = Firebase.firestore

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_de_habitacion)
        val menuLateral=findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility= NavigationView.INVISIBLE
        auth = Firebase.auth

        val idHotel= intent.getStringExtra("idHotel")
        val nombreHotel=intent.getStringExtra("nombreHotel")

        val listaTipoDeHabitacion = ArrayList<Habitaciones>()
        val recyclerViewTipoDeHabitacion= findViewById<RecyclerView>(R.id.rv_listaTiposHabitaciones)
        val adapter= Rcv_Habitaciones(this,recyclerViewTipoDeHabitacion,listaTipoDeHabitacion,nombreHotel!!)



        db.collection("TipoHabitacion").whereEqualTo("idHotel","${idHotel}").get()
            .addOnSuccessListener {
                for (habitacion in it){
                    listaTipoDeHabitacion.add(
                        Habitaciones(
                            habitacion.id.toString(),
                            habitacion.getString("idHotel"),
                            habitacion.getString("nombreHotel"),
                            habitacion.getDouble("numCamas")!!.toInt(),
                            habitacion.getDouble("numHabitaciones")!!.toInt(),
                            habitacion.getDouble("numMinAdultos")!!.toInt(),
                            habitacion.getDouble("numMaxAdultos")!!.toInt(),
                            habitacion.getDouble("numMaxNinios")!!.toInt(),
                            habitacion.getDouble("precioNinio"),
                            habitacion.getDouble("precioAdulto"),
                            habitacion.getDouble("precioInicial"),

                            habitacion.data["servicios"] as ArrayList<String>?

                            )

                    )
                }
                recyclerViewTipoDeHabitacion.adapter=adapter
                recyclerViewTipoDeHabitacion.itemAnimator=androidx.recyclerview.widget.DefaultItemAnimator()
                recyclerViewTipoDeHabitacion.layoutManager=androidx.recyclerview.widget.LinearLayoutManager(this)
                adapter.notifyDataSetChanged()

            }

        val botonAbrirYcerrarMenu= findViewById<ImageView>(R.id.img_btn_menulateral)
        botonAbrirYcerrarMenu.setOnClickListener{
            if (menuLateral.visibility== NavigationView.INVISIBLE){
                menuLateral.visibility= NavigationView.VISIBLE
            }else{
                menuLateral.visibility= NavigationView.INVISIBLE
            }
        }

        val botonPerfilIcon=findViewById<ImageView>(R.id.tv_img_perfil)
        botonPerfilIcon.setOnClickListener{
            menuLateral.visibility= NavigationView.INVISIBLE
            startActivity(Intent(this,Perfil::class.java))
        }

        val botonPerfil = findViewById<TextView>(R.id.tv_btn_perfil)
        botonPerfil.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            startActivity(Intent(this,Perfil::class.java))
        }

        val botonFavoritos= findViewById<TextView>(R.id.tv_btn_favoritos)
        botonFavoritos.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            startActivity(Intent(this,Favoritos::class.java))
        }
        val botonReservar= findViewById<TextView>(R.id.tv_btn_reservar)
        botonReservar.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            startActivity(Intent(this,PreReserva::class.java))
        }
        val botonMisReservas= findViewById<TextView>(R.id.tv_btn_mis_reservas)
        botonMisReservas.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            startActivity(Intent(this,MisReservas::class.java))
        }
        val botonAyuda= findViewById<TextView>(R.id.tv_btn_ayuda)
        botonAyuda.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }
        val botonConfiguracion= findViewById<TextView>(R.id.tv_btn_configuracion)
        botonConfiguracion.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val botonCerrarSession= findViewById<TextView>(R.id.tv_btn_cerrar_session)
        botonCerrarSession.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

}