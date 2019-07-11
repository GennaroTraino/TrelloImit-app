package com.genny.trelloimitation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DATABASE
        var db: SQLiteDatabase = openOrCreateDatabase("trellodb", Context.MODE_PRIVATE, null)
        createdb(db)

        //LOGIN o Home Page
        var shared: SharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE)


            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()



        }



    fun createdb(db:SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS products (nome TEXT PRIMARY KEY, descrizione TEXT, ancora TEXT, utente TEXT, svolto INT)")

        db.execSQL("DELETE FROM users")
        db.execSQL("INSERT INTO users VALUES('pippo42','pippo42');")
        db.execSQL("INSERT INTO users VALUES('sasybello','sasybello');")
        db.execSQL("INSERT INTO users VALUES('belgatto','belgatto');")
        db.execSQL("INSERT INTO users VALUES('utente1','utente1');")
        db.execSQL("INSERT INTO users VALUES('utente2','utente2');")
        db.execSQL("INSERT INTO users VALUES('utente3','utente3');")
        db.execSQL("INSERT INTO users VALUES('utente4','utente4');")
        db.execSQL("INSERT INTO users VALUES('utente5','utente5');")
        db.execSQL("INSERT INTO users VALUES('utente6','utente6');")

        db.execSQL("DELETE FROM products")
        db.execSQL("INSERT INTO products VALUES ('Progetto TecWeb', 'Completare il progetto JoinTheOffer per esame di TecWeb', ' ', 'pippo42', 0)")
        db.execSQL("INSERT INTO products VALUES ('Fare La Spesa', 'Lista articoli da acuistare', ' ', 'pippo42', 0)")
        db.execSQL("INSERT INTO products VALUES ('Ricordare', 'Lista di impegni presi e To Do', ' ', 'pippo42', 0)")
        db.execSQL("INSERT INTO products VALUES ('Progetto Terminali', 'Completare il progetto JoinTheOffer per esame di Terminali', ' ', 'pippo42', 0)")

        db.execSQL("INSERT INTO products VALUES ('Imparare Flask', 'Imparare flask da Tutorialspoint', 'Progetto TecWeb', 'pippo42', 0)")
        db.execSQL("INSERT INTO products VALUES ('Funzione Join', 'Scrivere la funzione di join per il progetto', 'Progetto TecWeb', 'pippo42', 0)")
        db.execSQL("INSERT INTO products VALUES ('Abbellire Aspetto', 'Abbellire aspetto con Bootstrap', 'Progetto TecWeb', 'pippo42', 1)")



    }
}


