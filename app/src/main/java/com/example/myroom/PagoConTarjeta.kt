package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.math.BigInteger
import java.security.MessageDigest

class PagoConTarjeta : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_con_tarjeta)
        val menuLateral = findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility = NavigationView.INVISIBLE
        auth = Firebase.auth
        val idCabecera = intent.getStringExtra("idCabecera")

        val botonConfirmar = findViewById<TextView>(R.id.txt_btn_confirmarPagoTarjeta)
        botonConfirmar.setOnClickListener {
            val db = Firebase.firestore

            db.collection("PagoConTarjeta").add(
                hashMapOf(
                    "idUsuario" to "${auth.uid}",
                    "idReserva" to "${idCabecera}",
                    "titular" to "${hash(findViewById<TextView>(R.id.txt_propietarioTarjeta).text.toString())}",
                    "numeroTarjeta" to "${hash(findViewById<TextView>(R.id.txv_numeroTarjeta).text.toString())}",
                    "fechaExpiracion" to "${hash(findViewById<TextView>(R.id.txt_fechaExpTarjeta).text.toString())}",
                    "cvv" to "${hash(findViewById<TextView>(R.id.txt_cvvTarjeta).text.toString())}",
                )

            )
                .addOnSuccessListener {
                    db.collection("ReservaCabecera").document("${idCabecera}")
                        .update(
                            "estado", "vigente",
                            "metodoDePago", "pagoConTarjeta"
                        ).addOnSuccessListener {
                            startActivity(Intent(this, MisReservas::class.java))
                            finish()
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
            val intent = Intent(this, PreReserva::class.java)
            intent.putExtra("estado", "abierta")
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
    fun hash(dato:String):String{
        return BigInteger(1,MessageDigest.getInstance("MD5").digest(dato.toByteArray())).toString(16).padStart(32,'0')
    }

}