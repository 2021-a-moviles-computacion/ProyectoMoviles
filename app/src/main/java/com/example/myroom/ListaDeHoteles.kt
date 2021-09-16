package com.example.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class ListaDeHoteles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_hoteles)
        val menuLateral=findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility=NavigationView.INVISIBLE


        val botonAbrirYcerrarMenu= findViewById<ImageView>(R.id.img_btn_menulateral)
        botonAbrirYcerrarMenu.setOnClickListener{
            if (menuLateral.visibility==NavigationView.INVISIBLE){
                menuLateral.visibility=NavigationView.VISIBLE
            }else{
                menuLateral.visibility=NavigationView.INVISIBLE
            }
        }

        val botonFavoritos= findViewById<TextView>(R.id.tv_btn_favoritos)
        botonFavoritos.setOnClickListener {
            menuLateral.visibility=NavigationView.INVISIBLE
            startActivity(Intent(this,Favoritos::class.java))
        }





        /*val recyclerArchivos = findViewById<RecyclerView>(R.id.rcv_Archivos)

        recyclerArchivos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerArchivos.adapter = RecyclerViewAdapterArchivos(listaArchivos,this)*/


    }
}