package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListaDeHoteles : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val db =Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_hoteles)

        val menuLateral=findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility=NavigationView.INVISIBLE
        auth = Firebase.auth

        val botonAbrirYcerrarMenu= findViewById<ImageView>(R.id.img_btn_menulateral)
        botonAbrirYcerrarMenu.setOnClickListener{
            if (menuLateral.visibility==NavigationView.INVISIBLE){
                menuLateral.visibility=NavigationView.VISIBLE
            }else{
                menuLateral.visibility=NavigationView.INVISIBLE
            }
        }

        val botonPerfilIcon=findViewById<ImageView>(R.id.tv_img_perfil)
        botonPerfilIcon.setOnClickListener{
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,Perfil::class.java))
        }

        val botonPerfil = findViewById<TextView>(R.id.tv_btn_perfil)
        botonPerfil.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,Perfil::class.java))
        }

        val botonFavoritos= findViewById<TextView>(R.id.tv_btn_favoritos)
        botonFavoritos.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,Favoritos::class.java))
        }
        val botonReservar= findViewById<TextView>(R.id.tv_btn_reservar)
        botonReservar.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,PreReserva::class.java))
        }
        val botonMisReservas= findViewById<TextView>(R.id.tv_btn_mis_reservas)
        botonMisReservas.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,MisReservas::class.java))
        }
        val botonAyuda= findViewById<TextView>(R.id.tv_btn_ayuda)
        botonAyuda.setOnClickListener {

            db.collection("TipoHabitacion").add(hashMapOf(
                "idHotel" to "sFnB6eJ0UDGMBm6AtOE5",
                "nombre" to "Simple",
                "numCamas" to 1,
                "numHabitaciones" to 10,
                "numMaxAdultos" to 2,
                "numMaxNinios" to 2,
                "numMinAdultos" to 1,
                "precioAdulto" to 15.00,
                "precioNinio" to 8.00,
                "precioInicial" to 50,
                "servicios" to arrayListOf<String>(
                    "TV","Baño Privado","Balcón","Mini Nevera"
                )
            ))
                .addOnSuccessListener {
                    Log.i("firestore", "tipo habitacion registrada 1")
                }.addOnFailureListener {
                    Log.i("firestore","NO registrado tipo Habitacion 1")
                }
            db.collection("TipoHabitacion").add(hashMapOf(
                "idHotel" to "sFnB6eJ0UDGMBm6AtOE5",
                "nombre" to "Doble",
                "numCamas" to 2,
                "numHabitaciones" to 15,
                "numMaxAdultos" to 4,
                "numMaxNinios" to 4,
                "numMinAdultos" to 1,
                "precioAdulto" to 15.00,
                "precioNinio" to 8.00,
                "precioInicial" to 70,
                "servicios" to arrayListOf<String>(
                    "TV","Mini Nevera","Balcón","Cocina"
                )
            ))
                .addOnSuccessListener {
                    Log.i("firestore", "tipo habitacion registrada 2")
                }.addOnFailureListener {
                    Log.i("firestore","NO registrado tipo Habitacion 2")
                }
            db.collection("TipoHabitacion").add(hashMapOf(
                "idHotel" to "sFnB6eJ0UDGMBm6AtOE5",
                "nombre" to "Comfort",
                "numCamas" to 1,
                "numHabitaciones" to 5,
                "numMaxAdultos" to 2,
                "numMaxNinios" to 2,
                "numMinAdultos" to 1,
                "precioAdulto" to 15.00,
                "precioNinio" to 8.00,
                "precioInicial" to 50,
                "servicios" to arrayListOf<String>(
                    "TV","Jacuzzi","Aire Acondicionado","Balcón"
                )
            ))
                .addOnSuccessListener {
                    Log.i("firestore", "tipo habitacion registrada 3")
                }.addOnFailureListener {
                    Log.i("firestore","NO registrado tipo Habitacion 3")
                }


            menuLateral.visibility=NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }
        val botonConfiguracion= findViewById<TextView>(R.id.tv_btn_configuracion)
        botonConfiguracion.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val botonCerrarSession= findViewById<TextView>(R.id.tv_btn_cerrar_session)
        botonCerrarSession.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            auth.signOut()
            LoginManager.getInstance().logOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }





        /*val recyclerArchivos = findViewById<RecyclerView>(R.id.rcv_Archivos)

        recyclerArchivos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerArchivos.adapter = RecyclerViewAdapterArchivos(listaArchivos,this)*/


    }
}