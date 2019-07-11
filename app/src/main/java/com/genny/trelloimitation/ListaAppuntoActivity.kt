package com.genny.trelloimitation

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lista_appunto.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.content.Intent as Intent

class ListaAppuntoActivity : AppCompatActivity() {

    lateinit var mTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_appunto)

        val bundle: Bundle? = intent.extras
        val ancora:String = bundle!!.getString("nome").toString()

        //DATABASE
        var db : SQLiteDatabase = openOrCreateDatabase("trellodb", Context.MODE_PRIVATE,null)

        //SHARED PREFERENCE
        var shared: SharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE)

        var listview: ListView = findViewById(R.id.listview_todo)
        var list = mutableListOf<Item>()

        var username = shared.getString("username"," ")

        var RAW = "Select * from products where utente = '" + username  +"' AND ancora = '" + ancora + "'"
        var cursor: Cursor = db.rawQuery(RAW,null)
        while (cursor.moveToNext()) {
            var titolo = cursor.getString(0)
            var descrizione = cursor.getString(1)
            var ancora = cursor.getString(2)
            var svolto = cursor.getInt(4)

            list.add(Item(titolo,descrizione,ancora,svolto))
        }
        //ADAPTER
        listview.adapter = MyListAdapter(this, R.layout.row, list)

        //Click su Item
        listview.setOnItemClickListener { parent, view, position, id ->
            val title = list[position].titolo
            val svolto = list[position].svolto

            if (svolto == 0) {
                var RAW = "UPDATE products SET svolto = 1 WHERE nome = '" + title + "' AND ancora = '" + ancora + "'"
                db.execSQL(RAW)
                //controllo per padre appunto
                var cond:Int = 1
                for (i in list) {
                    if (i.svolto == 0 && i.ancora == ancora)
                    {
                        cond = 0
                    }
                }

                if (cond == 1){
                    var RAW = "UPDATE products SET svolto = 1 WHERE nome = '" + ancora + "'"
                    db.execSQL(RAW)
                }


            } else if (svolto == 1) {
                var RAW = "UPDATE products SET svolto = 0 WHERE nome = '" + title + "' AND ancora = '" + ancora + "'"
                db.execSQL(RAW)
            }

            val intent = Intent(this,ListaAppuntoActivity::class.java)
            intent.putExtra("nome",ancora)
            startActivity(intent)
            finish()
        }

        fab_b_sub.setOnClickListener {
            val intent = Intent(this,InserimentoSubActivity::class.java)
            intent.putExtra("nome",ancora)
            startActivity(intent)
            finish()
        }

        // MENU
        registerForContextMenu(listview)

        //SPEECH
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.ITALIAN
            }
        })

        speech_sub.setOnClickListener {
            var toSpeak:String = " Per " + ancora + ", "
            for (i in list){
                if (i.svolto == 0){
                    toSpeak = toSpeak + "Ti manca:  "  + i.titolo + ".!."
                }
            }
            toSpeak = toSpeak + ".E poi completerai"


            if (toSpeak == ("Ciao" + username + "Ti ricordo che.").toString() ){
                //if there is no text in edit text
                Toast.makeText(this, "Non Hai Impegni, aggiungine uno", Toast.LENGTH_SHORT).show()
            }
            else{
                //if there is text in edit text
                Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
                mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.setHeaderTitle("opzioni")

        menu.add(0,v!!.id,0,"Completato")
        menu.add(0,v.id,0,"Delete")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title == "Open") {
            Toast.makeText(this,"Completato",Toast.LENGTH_LONG).show()
        } else if (item.title == "Delete") {
            Toast.makeText(this,"Vuoi cancellare",Toast.LENGTH_LONG).show()
        }

        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
