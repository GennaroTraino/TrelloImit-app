package com.genny.trelloimitation

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_inserimento_sub.*
import android.content.Intent

class InserimentoSubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserimento_sub)

        val bundle: Bundle? = intent.extras
        val ancora:String = bundle!!.getString("nome").toString()

        //DATABASE
        var db : SQLiteDatabase = openOrCreateDatabase("trellodb", Context.MODE_PRIVATE,null)

        //SHARED PREFERENCE
        var shared: SharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE)
        var utente = shared.getString("username"," ")

        inserisci_sub.setOnClickListener {

            var titolo = edit_titolo.text.toString()
            var descrizione = edit_descrizione.text.toString()

            var RAW = "INSERT INTO products VALUES ('" + titolo +"','" + descrizione + "','" + ancora + "','" + utente + "',0)"
            db.execSQL(RAW)

            val intent = Intent(this,ListaAppuntoActivity::class.java)
            intent.putExtra("nome",ancora)
            startActivity(intent)
            finish()
        }


    }
}
