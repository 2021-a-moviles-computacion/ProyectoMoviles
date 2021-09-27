package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.objetos.ReservaHabitacion
import com.example.myroom.recyclerview.Rcv_prereserva
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PreReserva : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Firebase.firestore

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_reserva)
        val menuLateral = findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility = NavigationView.INVISIBLE
        auth = Firebase.auth

        val estado = intent.getStringExtra("estado")


        val listaPreReserva = ArrayList<ReservaHabitacion>()
        val reference = Firebase.storage.reference
        val recyclerViewPreReserva = findViewById<RecyclerView>(R.id.rv_listaPrereserva)
        val adapterPreReserva = Rcv_prereserva(this, recyclerViewPreReserva, listaPreReserva)
        if (estado=="abierta") {
            db.collection("ReservaCabecera").whereEqualTo("estado", "abierta").get()
                .addOnSuccessListener {
                    if (it.size() > 0) {
                        db.collection("ReservaHabitacion")
                            .whereEqualTo("idReservaCabecera", "${it.documents[0].id}").get()
                            .addOnSuccessListener { listaHabitaciones ->
                                var suma=0.0
                                for (habitacion in listaHabitaciones) {

                                    reference.child("TiposHabitaciones/${habitacion.data["idTipoDeHabitacion"]}/1.jpg")
                                        .getBytes(1024 * 1024 * 3)
                                        .addOnSuccessListener { imagen ->
                                            findViewById<TextView>(R.id.tv_preReserva_nombreHotel).text=it.documents[0].getString("nombreHotel")
                                            listaPreReserva.add(
                                                ReservaHabitacion(
                                                    habitacion.id,
                                                    imagen,
                                                    habitacion.getString("fechaEntrada"),
                                                    habitacion.getString("fechaSalida"),
                                                    habitacion.getString("idReservaCabecera"),
                                                    habitacion.getString("idTipoDeHabitacion"),
                                                    habitacion.getString("idUsuario"),
                                                    habitacion.getString("nombreTipoDeHabitacion"),
                                                    habitacion.getDouble("numeroDeAdultos")!!
                                                        .toInt(),
                                                    habitacion.getDouble("numeroDeCamas")!!.toInt(),
                                                    habitacion.getDouble("numeroDeCuartos")!!
                                                        .toInt(),
                                                    habitacion.getDouble("numeroDeDias")!!.toInt(),
                                                    habitacion.getDouble("numeroDeNin")!!.toInt(),
                                                    habitacion.getDouble("subtotal"),


                                                    )
                                            )
                                            recyclerViewPreReserva.adapter = adapterPreReserva
                                            recyclerViewPreReserva.itemAnimator =
                                                androidx.recyclerview.widget.DefaultItemAnimator()
                                            recyclerViewPreReserva.layoutManager =
                                                androidx.recyclerview.widget.LinearLayoutManager(
                                                    this
                                                )
                                            adapterPreReserva.notifyDataSetChanged()

                                        }
                                    suma= (suma + habitacion.getDouble("subtotal")!!)
                                }
                                findViewById<TextView>(R.id.txv_precioTotalPrereserva).text="$ ${suma}"
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Aun no ha registrado habitaciones",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }else{
            db.collection("ReservaCabecera").document("${estado}").get()
                .addOnSuccessListener {
                    if (it!= null) {
                        db.collection("ReservaHabitacion")
                            .whereEqualTo("idReservaCabecera", "${it.id}").get()
                            .addOnSuccessListener { listaHabitaciones ->
                                for (habitacion in listaHabitaciones) {

                                    reference.child("TiposHabitaciones/${habitacion.data["idTipoDeHabitacion"]}/1.jpg")
                                        .getBytes(1024 * 1024 * 3)
                                        .addOnSuccessListener { imagen ->
                                            listaPreReserva.add(
                                                ReservaHabitacion(
                                                    habitacion.id,
                                                    imagen,
                                                    habitacion.getString("fechaEntrada"),
                                                    habitacion.getString("fechaSalida"),
                                                    habitacion.getString("idReservaCabecera"),
                                                    habitacion.getString("idTipoDeHabitacion"),
                                                    habitacion.getString("idUsuario"),
                                                    habitacion.getString("nombreTipoDeHabitacion"),
                                                    habitacion.getDouble("numeroDeAdultos")!!
                                                        .toInt(),
                                                    habitacion.getDouble("numeroDeCamas")!!.toInt(),
                                                    habitacion.getDouble("numeroDeCuartos")!!
                                                        .toInt(),
                                                    habitacion.getDouble("numeroDeDias")!!.toInt(),
                                                    habitacion.getDouble("numeroDeNin")!!.toInt(),
                                                    habitacion.getDouble("subtotal"),


                                                    )
                                            )
                                            recyclerViewPreReserva.adapter = adapterPreReserva
                                            recyclerViewPreReserva.itemAnimator =
                                                androidx.recyclerview.widget.DefaultItemAnimator()
                                            recyclerViewPreReserva.layoutManager =
                                                androidx.recyclerview.widget.LinearLayoutManager(
                                                    this
                                                )
                                            adapterPreReserva.notifyDataSetChanged()
                                        }

                                }

                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Aun no ha registrado habitaciones",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }


        val botonAbrirYcerrarMenu = findViewById<ImageView>(R.id.img_btn_menulateral)
        botonAbrirYcerrarMenu.setOnClickListener {
            if (menuLateral.visibility == NavigationView.INVISIBLE) {
                menuLateral.visibility = NavigationView.VISIBLE
            } else {
                menuLateral.visibility = NavigationView.INVISIBLE
            }
        }

        val botonPerfilIcon = findViewById<ImageView>(R.id.tv_img_perfil)
        botonPerfilIcon.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            startActivity(Intent(this, Perfil::class.java))
        }

        val botonPerfil = findViewById<TextView>(R.id.tv_btn_perfil)
        botonPerfil.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            startActivity(Intent(this, Perfil::class.java))
        }

        val botonFavoritos = findViewById<TextView>(R.id.tv_btn_favoritos)
        botonFavoritos.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            startActivity(Intent(this, Favoritos::class.java))
        }
        val botonReservar = findViewById<TextView>(R.id.tv_btn_reservar)
        botonReservar.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            val intent =Intent(this,PreReserva::class.java)
            intent.putExtra("estado","abierta")
            startActivity(intent)
        }
        val botonMisReservas = findViewById<TextView>(R.id.tv_btn_mis_reservas)
        botonMisReservas.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            startActivity(Intent(this, MisReservas::class.java))
        }
        val botonAyuda = findViewById<TextView>(R.id.tv_btn_ayuda)
        botonAyuda.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }
        val botonConfiguracion = findViewById<TextView>(R.id.tv_btn_configuracion)
        botonConfiguracion.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            Toast.makeText(
                baseContext, "NO ESTA DISPONIBLE EN LA VERSION ACTUAL.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val botonCerrarSession = findViewById<TextView>(R.id.tv_btn_cerrar_session)
        botonCerrarSession.setOnClickListener {
            menuLateral.visibility = NavigationView.INVISIBLE
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}