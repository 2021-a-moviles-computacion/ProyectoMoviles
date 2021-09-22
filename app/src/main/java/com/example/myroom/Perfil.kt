package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.*
import android.widget.TextView.*

import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Perfil : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    val db =Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        val menuLateral=findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility= NavigationView.INVISIBLE

        auth = Firebase.auth



        val textoaceptar = switchEnable()

        val botonEditar=findViewById<TextView>(R.id.txt_btn_editar_perfil)
        botonEditar.setOnClickListener {
            botonEditar.text=switchEnable()
            if(botonEditar.text=="Editar") {
                db.collection("Usuario")
                    .document(auth.currentUser!!.uid)
                    .update(
                        "genero",
                        findViewById<EditText>(R.id.txt_perfil_genero).text.toString(),
                        "nacionalidad",
                        findViewById<EditText>(R.id.txt_perfil_nacionalidad).text.toString(),
                        "descripcion",
                        findViewById<EditText>(R.id.txt_perfil_descripcion_personal).text.toString()
                    ).addOnSuccessListener {
                        Toast.makeText(
                            baseContext, "Las datos han sido actualizados",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Log.i("firestore", "Error ${it.toString()}")
                    }
            }


        }
        val botonCancelar=findViewById<TextView>(R.id.txt_btn_cancelar_editar_perfil)
        botonCancelar.setOnClickListener {
            botonEditar.text=switchEnable()
        }




        db.collection("Usuario").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { DocumentSnapshot->
                findViewById<TextView>(R.id.tv_nombrePerfil).text=DocumentSnapshot.getString("nombre")+" "+DocumentSnapshot.getString("apellido")
                findViewById<EditText>(R.id.txt_perfil_email).setText(DocumentSnapshot.getString("correo"))
                findViewById<EditText>(R.id.txt_perfil_genero).setText(DocumentSnapshot.getString("genero"))
                findViewById<EditText>(R.id.txt_perfil_nacionalidad).setText(DocumentSnapshot.getString("nacionalidad"))
                findViewById<EditText>(R.id.txt_perfil_descripcion_personal).setText(DocumentSnapshot.getString("descripcion"))
                findViewById<EditText>(R.id.txt_perfil_lugares_visitados).setText(DocumentSnapshot.getString("lugaresVisitados"))

            }
            .addOnFailureListener { }

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

        }

        val botonPerfil = findViewById<TextView>(R.id.tv_btn_perfil)
        botonPerfil.setOnClickListener {
            menuLateral.visibility= NavigationView.INVISIBLE

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

    private fun switchEnable():String {
        if(findViewById<TextView>(R.id.txt_perfil_genero).isEnabled){
            findViewById<TextView>(R.id.txt_perfil_lugares_visitados).isEnabled=false
            findViewById<TextView>(R.id.txt_perfil_email).isEnabled=false
            findViewById<TextView>(R.id.txt_perfil_genero).isEnabled=false
            findViewById<TextView>(R.id.txt_perfil_descripcion_personal).isEnabled=false
            findViewById<TextView>(R.id.txt_perfil_nacionalidad).isEnabled=false
            findViewById<TextView>(R.id.txt_btn_cancelar_editar_perfil).visibility= INVISIBLE
            return "Editar"


        }else{
            //findViewById<TextView>(R.id.txt_perfil_lugares_visitados).isEnabled=true
           // findViewById<TextView>(R.id.txt_perfil_email).isEnabled=true
            findViewById<TextView>(R.id.txt_perfil_genero).isEnabled=true
            findViewById<TextView>(R.id.txt_perfil_descripcion_personal).isEnabled=true
            findViewById<TextView>(R.id.txt_perfil_nacionalidad).isEnabled=true
            findViewById<TextView>(R.id.txt_btn_cancelar_editar_perfil).visibility= VISIBLE
            return "Guardar"
        }

    }
}