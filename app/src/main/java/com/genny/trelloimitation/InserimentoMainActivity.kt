package com.genny.trelloimitation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inserimento_main.*
import kotlinx.android.synthetic.main.activity_inserimento_sub.*

class InserimentoMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserimento_main)

        //DATABASE
        var db : SQLiteDatabase = openOrCreateDatabase("trellodb", Context.MODE_PRIVATE,null)

        //SHARED PREFERENCE
        var shared: SharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE)
        var utente = shared.getString("username"," ")

        inserisci_main.setOnClickListener {
            var titolo = titolo_main.text.toString()
            var descrizione = descrizione_main.text.toString()


            var RAW = "INSERT INTO products VALUES('" + titolo +"','" + descrizione +"',' ','" + utente + "',0)"
            db.execSQL(RAW)

            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        annulla_main.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
