package com.example.myroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.navigation.NavigationView

class Favoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val menuLateral=findViewById<NavigationView>(R.id.nv_menu_lateral)
        menuLateral.visibility= NavigationView.INVISIBLE


        val botonAbrirYcerrarMenu= findViewById<ImageView>(R.id.img_btn_menulateral)
        botonAbrirYcerrarMenu.setOnClickListener{
            if (menuLateral.visibility== NavigationView.INVISIBLE){
                menuLateral.visibility= NavigationView.VISIBLE
            }else{
                menuLateral.visibility= NavigationView.INVISIBLE
            }
        }

    }

}