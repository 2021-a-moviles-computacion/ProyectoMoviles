package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registarse : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registarse)

        auth = Firebase.auth
        val botonIrLogin = findViewById<TextView>(R.id.txt_btn_IrInicioSession)
        botonIrLogin.setOnClickListener {
           startActivity(Intent(this,MainActivity::class.java))

            finish()
        }
        val botonSalir = findViewById<Button>(R.id.btn_SalirApp)
        botonSalir.setOnClickListener {
            System.exit(0)
        }
        val botonRegistarse= findViewById<Button>(R.id.btn_RegistrarseApp)
        botonRegistarse.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                findViewById<EditText>(R.id.txt_CorreoRegistrar).text.toString(),
                findViewById<EditText>(R.id.txt_ContraseñaRegistrar).text.toString(),
                )
                .addOnCompleteListener (this){ task->
                    if(task.isSuccessful){
                        Log.i("firebaseauth","Usuario Registrado Con exito")
                        Toast.makeText(baseContext, "Usuario registrado con exito.",
                            Toast.LENGTH_SHORT).show()
                        //val user = auth.currentUser

                        startActivity(Intent(this,ListaDeHoteles::class.java))
                        finish()
                    }else{
                        Log.i("firebaseauth","Error ${task.exception}")
                        Toast.makeText(baseContext, "Error registrando Usuario.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }


}