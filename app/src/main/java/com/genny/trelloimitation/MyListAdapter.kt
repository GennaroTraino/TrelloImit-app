package com.genny.trelloimitation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MyListAdapter(var cnt: Context, var resource:Int, var items:List<Item>)
    :ArrayAdapter<Item>(cnt,resource,items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(cnt)

        val view: View = layoutInflater.inflate(resource,null)

        val titolo: TextView = view.findViewById(R.id.titolo_main)
        val descrizione: TextView = view.findViewById(R.id.descrizione_main)
        val image: ImageView = view.findViewById(R.id.imageview_main)

        val item: Item = items[position]

        titolo.text = item.titolo


        descrizione.text = item.descrizione
        var svolto = item.svolto

        if (svolto == 1) {
            image.setImageResource(R.drawable.ic_done_box_24dp)
        } else {
            image.setImageResource(R.drawable.ic_notdone_box_24dp)
        }



        return view

    }

}